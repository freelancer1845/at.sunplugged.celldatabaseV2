package at.sunplugged.celldatabaseV2.sprodaccess.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessException;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessService;
import datamodel.CellResult;

@Component(immediate = true)
public class SprodAccessServiceImpl implements SprodAccessService {

  private static final Logger LOG = LoggerFactory.getLogger(SprodAccessServiceImpl.class);

  private SprodConnection sprodConnection;

  @Activate
  protected void activate() {
    try {
      Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    } catch (ClassNotFoundException e) {
      LOG.error("Could not find UcanaccessDriver in classpath!!!", e);
    }
  }

  @Override
  public void openFile(File file) throws SprodAccessException {
    LOG.debug("Opening Microsoft Access File: " + file.getAbsolutePath());

    try {
      sprodConnection = new SprodConnection(getUcanaccessConnection(file.getAbsolutePath()));
    } catch (SQLException e) {
      LOG.error("SQLException: " + e.getMessage());
      throw new SprodAccessException("Failed to open File: " + file.getAbsolutePath(), e);
    }

  }

  @Override
  public void close() {
    LOG.debug("Closing Microsoft Access connection...");
  }

  private Connection getUcanaccessConnection(String pathToDb) throws SQLException {
    String url = "jdbc:ucanaccess://" + pathToDb;
    return DriverManager.getConnection(url);
  }

  @Override
  public CellResult getById(int id) throws SprodAccessException {
    checkConnection();
    return sprodConnection.getById(id);
  }

  @Override
  public List<CellResult> getByIds(int[] ids) throws SprodAccessException {
    checkConnection();
    List<CellResult> list = new LinkedList<>();
    for (int id : ids) {
      list.add(getById(id));
    }
    return list;
  }

  @Override
  public List<CellResult> getAll() throws SprodAccessException {
    checkConnection();
    int maxID = sprodConnection.getMaxId();
    int[] ids = new int[maxID];
    for (int i = 0; i < maxID; i++) {
      ids[i] = i + 1;
    }
    return getByIds(ids);
  }

  private void checkConnection() throws SprodAccessException {
    if (sprodConnection == null) {
      throw new SprodAccessException("No Connection created...");
    }
    try {
      if (sprodConnection.isOpen() == false) {
        throw new SprodAccessException("Sprod Connection not open...");
      }
    } catch (SQLException e) {
      throw new SprodAccessException("Failed to check if connection is open...");
    }
  }



}


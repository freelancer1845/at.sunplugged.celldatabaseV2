package at.sunplugged.celldatabaseV2.persistence.xmi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException;
import org.eclipse.emf.ecore.xmi.FeatureNotFoundException;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.common.settings.SettingsAccessor;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;
import datamodel.Database;
import datamodel.DatamodelFactory;

@Component
public class DatabaseServiceImpl implements DatabaseService {

  private final static Logger LOG = LoggerFactory.getLogger(DatabaseServiceImpl.class);

  private Database database;

  private ComposedAdapterFactory composedAdapterFactory;

  private AdapterFactoryEditingDomain editingDomain;

  private Resource resource;

  public DatabaseServiceImpl() {
    editingDomain = new AdapterFactoryEditingDomain(getAdapterFactory(), new BasicCommandStack());
  }



  @Override
  public void openDatabase(String path) throws DatabaseServiceException {
    unloadDatabase();
    resource = createXmiResource(path);
    database = loadXmiDatabase();
    if (database == null) {
      database = createDefaultDatabase();
    }
    addToRecentDatabases(path);



  }

  private void addToRecentDatabases(String path) {
    Set<String> recentDatabases = SettingsAccessor.getInstance().getSettings().getRecentDatabases();

    if (recentDatabases.contains(path)) {
      recentDatabases.remove(path);
    }
    SettingsAccessor.getInstance().getSettings().getRecentDatabases().add(path);
    try {
      SettingsAccessor.getInstance().flushSettings();
    } catch (IOException e) {
      LOG.warn("Flushing settings after adding recent database failed.", e);
    }


  }



  @Override
  public Database getDatabase() {
    return database;
  }

  private void unloadDatabase() {
    if (resource != null) {
      resource.unload();
      database = null;
      resource = null;
    }
  }

  private Database loadXmiDatabase() {
    if (resource.getContents().isEmpty() == false) {
      return (Database) resource.getContents().get(0);
    } else {
      return null;
    }
  }

  private Database createDefaultDatabase() {

    Database database = DatamodelFactory.eINSTANCE.createDatabase();

    resource.getContents().add(database);
    database = (Database) resource.getContents().get(0);
    return database;
  }

  private Resource createXmiResource(String path) throws DatabaseServiceException {
    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
    Map<String, Object> m = reg.getExtensionToFactoryMap();
    m.put("xmi", new XMIResourceFactoryImpl());
    File file = new File(path);
    if (file.exists() == false) {
      try {
        LOG.debug("Database file didnt exist.... creating new one: " + file.getAbsolutePath());
        if (file.getParentFile().mkdirs() == false) {
          throw new IOException("Failed to create direcotries...");
        }
        file.createNewFile();
      } catch (IOException e) {
        LOG.error("Failed to create database file.", e);
        throw new DatabaseServiceException("Failed to create database file");
      }
    }
    Resource resource = editingDomain.createResource("file://" + file.getAbsolutePath());
    try {
      resource.load(Collections.EMPTY_MAP);
    } catch (IOException e) {
      LOG.debug("Failed to load resource...", e);
      LOG.debug("Assuming it does not exist...");
      try {
        resource.save(Collections.EMPTY_MAP);
      } catch (IOException e1) {
        LOG.error("Failed to create xmi resource...");
        throw new DatabaseServiceException(e);
      }

    }
    return resource;
  }

  private AdapterFactory getAdapterFactory() {
    if (composedAdapterFactory == null) {
      composedAdapterFactory =
          new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
    }
    return composedAdapterFactory;
  }

  private Resource loadXmiResource(String path) {
    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
    Factory xmiFactory = (Factory) reg.getExtensionToFactoryMap().get("xmi");

    Resource resource = xmiFactory.createResource(URI.createFileURI(path));
    return resource;
  }

  @Override
  public void saveDatabase() throws DatabaseServiceException {
    LOG.debug("Saving database...");
    try {
      resource.save(Collections.EMPTY_MAP);
    } catch (IOException e) {
      LOG.error("Failed to save Resource...", e);
      throw new DatabaseServiceException(e);
    }
    LOG.debug("Save completed...");
  }

  @Override
  public void importDatabase(String path) throws DatabaseServiceException {

    if (getDatabase() == null) {
      throw new IllegalArgumentException("No database loaded...");
    }
    Resource resource = loadXmiResource(path);
    try {
      resource.load(Collections.EMPTY_MAP);

    } catch (IOWrappedException eF) {
      if (eF.getCause() instanceof FeatureNotFoundException) {
        LOG.debug("Feature not found." + eF.getMessage());
      } else {
        LOG.error("Failed to load Database to import... \"" + path + "\"", eF);
        throw new DatabaseServiceException("Failed to load Database...", eF);
      }

    } catch (IOException e) {
      LOG.error("Failed to load Database to import... \"" + path + "\"", e);
      throw new DatabaseServiceException("Failed to load Database...", e);
    }
    Database databaseToImport = (Database) resource.getContents().get(0);
    Command cmd =
        AddCommand.create(editingDomain, getDatabase(), null, databaseToImport.getCellGroups());

    editingDomain.getCommandStack().execute(cmd);
    resource.unload();
  }

  @Override
  public void saveDatabase(String path) throws DatabaseServiceException {
    try {
      LOG.debug("Saving database to (new) file");
      File file = new File(path);
      if (file.exists() == false) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }

      if (file.exists() == false) {
        throw new IllegalStateException("Failed to create file...");
      }

      FileOutputStream outputStream = new FileOutputStream(file);

      resource.save(outputStream, Collections.EMPTY_MAP);
      openDatabase(path);
    } catch (IOException e) {
      LOG.error("Failed to save Database...", e);
      throw new DatabaseServiceException("Failed to save Database...", e);
    }


  }



}

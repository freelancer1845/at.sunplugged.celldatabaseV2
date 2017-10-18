package at.sunplugged.celldatabaseV2.sprodaccess.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.sprodaccess.api.SprodAccessException;
import datamodel.CellMeasurementDataSet;
import datamodel.CellResult;
import datamodel.DatamodelFactory;
import datamodel.UIDataPoint;

public class SprodConnection {

  private static final Logger LOG = LoggerFactory.getLogger(SprodConnection.class);

  private final Connection connection;

  public SprodConnection(Connection connection) {
    this.connection = connection;
  }

  public int getMaxId() throws SprodAccessException {
    Statement stm;
    try {
      stm = connection.createStatement();
      ResultSet result;
      try {
        String sql = "SELECT MAX(ID) FROM Mes";

        result = stm.executeQuery(sql);

        try {
          if (result.next() == false) {
            throw new SprodAccessException("Result is empty...");
          }
          return result.getInt(1);
        } catch (SQLException e) {
          LOG.error("Failed to get value from result...");
          throw new SprodAccessException(e);
        }
      } catch (SQLException e) {
        LOG.error("Failed to execute Statement...", e);
        throw new SprodAccessException(e);
      }
    } catch (SQLException e) {
      LOG.error("Failed to create Statement...", e);
      throw new SprodAccessException(e);
    }

  }


  public CellResult getById(int id) throws SprodAccessException {
    CellResult result = DatamodelFactory.eINSTANCE.createCellResult();

    String sql = "SELECT Id,ResId,CelId,Mid,DtCr FROM Mes WHERE ID = " + id;

    Statement stm;
    try {
      stm = connection.createStatement();
      try {
        ResultSet idRow = stm.executeQuery(sql);
        if (idRow.next() == false) {
          throw new SprodAccessException("No result saved for the particular Id=\"" + id + "\"");
        }
        sql = "SELECT RsVoc,RsIsc,RsVpm,RsIpm,RsMxe,RsEff,RsRsr,RsRsh FROM MesRes WHERE ID="
            + idRow.getInt(2);
        ResultSet mesRow = stm.executeQuery(sql);

        if (mesRow.next() == false) {
          throw new SprodAccessException("No measurement data found for Id=\"" + id + "\"");
        }
        result.setName(idRow.getString(1));
        result.setOpenCircuitVoltage(mesRow.getDouble(1));
        result.setShortCircuitCurrent(mesRow.getDouble(2));
        result.setMaximumPowerVoltage(mesRow.getDouble(3));
        result.setMaximumPowerCurrent(mesRow.getDouble(4));
        result.setEfficiency(mesRow.getDouble(5) * 100);
        result.setFillFactor(mesRow.getDouble(6) * 100);
        result.setParallelResistance(mesRow.getDouble(7));
        result.setSeriesResistance(mesRow.getDouble(8));

        sql = String.format(
            "SELECT Cv,Cc FROM MesResPts WHERE MesId=%d AND MesTypId=1 AND SubMesId=1 ORDER BY Id",
            idRow.getInt(1));
        ResultSet lightData = stm.executeQuery(sql);

        CellMeasurementDataSet lightDataSet =
            DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();

        lightDataSet.setName(result.getName() + " (light)");
        List<UIDataPoint> lightDataList = new ArrayList<>();
        while (lightData.next() == true) {
          UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
          dataPoint.setVoltage(lightData.getDouble(1));
          dataPoint.setCurrent(lightData.getDouble(2));
          lightDataList.add(dataPoint);
        }
        // Collections.sort(lightDataList, (a, b) -> Double.compare(a.getVoltage(),
        // b.getVoltage()));

        lightDataSet.getData().addAll(lightDataList);
        result.setLightMeasurementDataSet(lightDataSet);

        sql = String.format(
            "SELECT Cv,Cc FROM MesResPts WHERE MesId=%d AND MesTypId=4 AND SubMesId=1 ORDER BY Id",
            idRow.getInt(1));

        ResultSet darkData = stm.executeQuery(sql);

        CellMeasurementDataSet darkDataSet =
            DatamodelFactory.eINSTANCE.createCellMeasurementDataSet();
        List<UIDataPoint> darkDataList = new ArrayList<>();
        while (darkData.next() == true) {
          UIDataPoint dataPoint = DatamodelFactory.eINSTANCE.createUIDataPoint();
          dataPoint.setVoltage(darkData.getDouble(1));
          dataPoint.setCurrent(darkData.getDouble(2));
          darkDataList.add(dataPoint);

        }
        // Collections.sort(darkDataList, (a, b) -> Double.compare(a.getVoltage(), b.getVoltage()));
        darkDataSet.setName(result.getName() + " (dark)");
        darkDataSet.getData().addAll(darkDataList);
        if (darkDataList.isEmpty() == false) {
          result.setDarkMeasuremenetDataSet(darkDataSet);
        } else {
          LOG.warn("No dark data found for Sprod data with id: " + id);
        }
        sql = String.format("SELECT Surf FROM MesCel WHERE ID = %d", idRow.getInt(3));
        ResultSet surfResultSet = stm.executeQuery(sql);
        if (surfResultSet.next() == false) {
          throw new SprodAccessException("Failed to retrieve Area of result!");
        }

        lightDataSet.setArea(surfResultSet.getDouble(1));
        darkDataSet.setArea(surfResultSet.getDouble(1));

      } catch (SQLException e) {
        LOG.error("Failed to execute query:\"" + sql + "\"", e);
        throw new SprodAccessException(e);
      }
    } catch (SQLException e) {
      LOG.error("Failed to get Statement..." + e);
      throw new SprodAccessException(e);
    }



    return result;
  }


  public boolean isOpen() throws SQLException {
    return connection.isClosed() == false;
  }



}


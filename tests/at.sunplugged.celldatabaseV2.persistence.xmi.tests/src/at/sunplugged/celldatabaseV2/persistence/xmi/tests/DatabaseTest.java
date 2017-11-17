package at.sunplugged.celldatabaseV2.persistence.xmi.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.junit.Before;
import org.junit.Test;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseServiceException;
import at.sunplugged.celldatabaseV2.persistence.xmi.DatabaseServiceImpl;
import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelFactory;

public class DatabaseTest {

  private DatabaseService databaseService;

  @Before
  public void setup() throws DatabaseServiceException {
    databaseService = new DatabaseServiceImpl();
    databaseService.openDatabase("database/Database");
  }

  @Test
  public void databaseNotNull() throws DatabaseServiceException {
    assertNotNull(databaseService.getDatabase());
  }

  @Test
  public void hasEditingDomain() throws DatabaseServiceException {
    Database database = databaseService.getDatabase();
    assertNotNull(AdapterFactoryEditingDomain.getEditingDomainFor(database));
  }

  @Test
  public void savesData() throws DatabaseServiceException {
    Database database = databaseService.getDatabase();
    CellGroup group = DatamodelFactory.eINSTANCE.createCellGroup();
    database.getCellGroups()
        .add(group);
    databaseService.saveDatabase();

    DatabaseService tempService = new DatabaseServiceImpl();
    tempService.openDatabase("database/Database");
    Database tempDatabase = tempService.getDatabase();
    assertTrue("Database was empty after loading...", tempDatabase.getCellGroups()
        .size() > 0);
  }


}

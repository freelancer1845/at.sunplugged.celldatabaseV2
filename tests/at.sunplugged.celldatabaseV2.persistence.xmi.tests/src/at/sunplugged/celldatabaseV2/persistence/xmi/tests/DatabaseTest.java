package at.sunplugged.celldatabaseV2.persistence.xmi.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.junit.Before;
import org.junit.Test;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.xmi.DatabaseServiceImpl;
import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelFactory;

public class DatabaseTest {

  private DatabaseService databaseService;

  @Before
  public void setup() {
    databaseService = new DatabaseServiceImpl();
  }

  @Test
  public void databaseNotNull() {
    assertNotNull(databaseService.getDatabase());
  }

  @Test
  public void hasEditingDomain() {
    Database database = databaseService.getDatabase();
    assertNotNull(AdapterFactoryEditingDomain.getEditingDomainFor(database));
  }

  @Test
  public void savesData() {
    Database database = databaseService.getDatabase();
    CellGroup group = DatamodelFactory.eINSTANCE.createCellGroup();
    group.setName("TestGroup");
    database.getCellGroups().add(group);
    databaseService.saveDatabase();

    DatabaseService tempService = new DatabaseServiceImpl();
    Database tempDatabase = tempService.getDatabase();
    assertTrue("TestGroup".equals(tempDatabase.getCellGroups().get(0).getName()));
  }


}

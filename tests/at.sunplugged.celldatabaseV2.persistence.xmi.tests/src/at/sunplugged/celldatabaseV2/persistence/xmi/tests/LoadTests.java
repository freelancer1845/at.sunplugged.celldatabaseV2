package at.sunplugged.celldatabaseV2.persistence.xmi.tests;

import static org.junit.Assert.assertNotNull;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.junit.Before;
import org.junit.Test;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import at.sunplugged.celldatabaseV2.persistence.xmi.DatabaseServiceImpl;
import datamodel.Database;

public class LoadTests {

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


}

package at.sunplugged.celldatabaseV2.persistence.xmi;

import org.osgi.service.component.annotations.Component;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import datamodel.Database;
import datamodel.DatamodelFactory;

@Component
public class DatabaseServiceImpl implements DatabaseService {

  public DatabaseServiceImpl() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Database getDatabase() {
    return DatamodelFactory.eINSTANCE.createDatabase();
  }

  @Override
  public void saveDatabase() {

  }

}

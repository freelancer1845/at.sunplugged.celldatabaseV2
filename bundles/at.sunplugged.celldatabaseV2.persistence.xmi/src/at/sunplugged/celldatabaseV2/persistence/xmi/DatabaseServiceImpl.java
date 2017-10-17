package at.sunplugged.celldatabaseV2.persistence.xmi;

import java.util.Map;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.osgi.service.component.annotations.Component;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
import datamodel.CellGroup;
import datamodel.Database;
import datamodel.DatamodelFactory;

@Component
public class DatabaseServiceImpl implements DatabaseService {

  private Database database;

  private ComposedAdapterFactory composedAdapterFactory;

  private AdapterFactoryEditingDomain editingDomain;

  public DatabaseServiceImpl() {
    editingDomain = new AdapterFactoryEditingDomain(getAdapterFactory(), new BasicCommandStack());
  }

  @Override
  public Database getDatabase() {
    if (database == null) {
      database = createDefaultDatabase();
    }
    return database;
  }

  private Database createDefaultDatabase() {


    Resource resource = createXmiResource();
    Database database = DatamodelFactory.eINSTANCE.createDatabase();

    CellGroup cellGroup = DatamodelFactory.eINSTANCE.createCellGroup();

    cellGroup.setName("Default Name");

    database.getCellGroups().add(cellGroup);

    resource.getContents().add(database);

    return database;
  }

  private Resource createXmiResource() {
    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
    Map<String, Object> m = reg.getExtensionToFactoryMap();

    m.put("database", new XMIResourceFactoryImpl());

    Resource resource = editingDomain.createResource("database/DefaultDatabase.database");
    return resource;
  }

  private AdapterFactory getAdapterFactory() {
    if (composedAdapterFactory == null) {
      composedAdapterFactory =
          new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
    }
    return composedAdapterFactory;
  }

  @Override
  public void saveDatabase() {

  }

}

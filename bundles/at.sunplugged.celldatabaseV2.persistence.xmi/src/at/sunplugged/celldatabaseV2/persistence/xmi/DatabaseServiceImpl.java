package at.sunplugged.celldatabaseV2.persistence.xmi;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.sunplugged.celldatabaseV2.persistence.api.DatabaseService;
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
  public Database getDatabase() {
    resource = createXmiResource();
    database = loadXmiDatabase();
    if (database == null) {
      database = createDefaultDatabase();
    }
    return database;
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

  private Resource createXmiResource() {
    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
    Map<String, Object> m = reg.getExtensionToFactoryMap();

    m.put("database", new XMIResourceFactoryImpl());

    Resource resource = editingDomain.createResource("database/DefaultDatabase.database");
    try {
      resource.load(Collections.EMPTY_MAP);
    } catch (IOException e) {
      LOG.error("Failed to load resource...");
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

  @Override
  public void saveDatabase() {
    try {
      resource.save(Collections.EMPTY_MAP);
    } catch (IOException e) {
      LOG.error("Failed to save Resource...", e);
    }
  }

}

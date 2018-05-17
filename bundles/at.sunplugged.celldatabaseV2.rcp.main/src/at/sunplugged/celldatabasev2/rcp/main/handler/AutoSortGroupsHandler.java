
package at.sunplugged.celldatabasev2.rcp.main.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import at.sunplugged.celldatabaseV2.common.settings.SettingsAccessor;
import datamodel.Database;
import datamodel.DatamodelPackage;
import datamodel.util.DatamodelUtils;

public class AutoSortGroupsHandler {



  @Execute
  public void execute(MItem item, Database database) {
    if (item.isSelected() == true) {
      activate(database);
    } else {
      deactivate(database);
    }

  }


  public void activate(Database database) {
    EList<Adapter> list = database.eAdapters();
    if (alreadyContainsAutoSortAdapter(list) == false) {
      AutoSortAdapter adapter = new AutoSortAdapter();
      adapter.sortDatabase(database);
      list.add(new AutoSortAdapter());
    }
    SettingsAccessor.getInstance().getSettings().setAutoSortGroups(true);
    SettingsAccessor.getInstance().flushSettingsIgnore();
  }

  public void deactivate(Database database) {
    EList<Adapter> list = database.eAdapters();
    if (alreadyContainsAutoSortAdapter(list)) {
      removeAutoSortAdapters(list);
    }
    SettingsAccessor.getInstance().getSettings().setAutoSortGroups(false);
    SettingsAccessor.getInstance().flushSettingsIgnore();
  }

  private void removeAutoSortAdapters(EList<Adapter> list) {
    list.removeIf(adapter -> adapter instanceof AutoSortAdapter);
  }


  private final class AutoSortAdapter extends AdapterImpl {
    @Override
    public void notifyChanged(Notification msg) {
      if (msg.getFeature() != null) {
        if (msg.getFeature().equals(DatamodelPackage.Literals.DATABASE__CELL_GROUPS)) {
          Database database = (Database) msg.getNotifier();
          sortDatabase(database);
        }
      }

    }

    private void sortDatabase(Database database) {
      ECollections.sort(database.getCellGroups(),
          DatamodelUtils.Comparators.compareGroupsNatural());
      database.getCellGroups().forEach(group -> System.out.println(group.getName()));
    }
  }

  private boolean alreadyContainsAutoSortAdapter(EList<Adapter> list) {
    return list.stream().anyMatch(adapter -> adapter instanceof AutoSortAdapter);
  }

}

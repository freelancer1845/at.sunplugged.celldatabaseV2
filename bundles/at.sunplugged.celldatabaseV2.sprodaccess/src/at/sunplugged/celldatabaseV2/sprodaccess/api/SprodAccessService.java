package at.sunplugged.celldatabaseV2.sprodaccess.api;

import java.io.File;
import java.util.List;
import datamodel.CellResult;

public interface SprodAccessService {
  void openFile(File file) throws SprodAccessException;

  CellResult getById(int id) throws SprodAccessException;

  List<CellResult> getByIds(int[] ids) throws SprodAccessException;

  List<CellResult> getAll() throws SprodAccessException;

  void close();
}

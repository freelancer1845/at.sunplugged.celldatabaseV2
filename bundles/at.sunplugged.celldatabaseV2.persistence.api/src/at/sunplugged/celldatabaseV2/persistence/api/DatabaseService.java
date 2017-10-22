package at.sunplugged.celldatabaseV2.persistence.api;

import datamodel.Database;

public interface DatabaseService {

  void openDatabase(String path) throws DatabaseServiceException;

  Database getDatabase();

  void saveDatabase() throws DatabaseServiceException;


}

package at.sunplugged.celldatabaseV2.persistence.api;

import datamodel.Database;

public interface DatabaseService {

  Database getDatabase() throws DatabaseServiceException;

  void saveDatabase() throws DatabaseServiceException;


}

package at.sunplugged.celldatabaseV2.persistence.api;

public class DatabaseServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 5008414022422264079L;

  public DatabaseServiceException() {}

  public DatabaseServiceException(String arg0) {
    super(arg0);
  }

  public DatabaseServiceException(Throwable arg0) {
    super(arg0);
  }

  public DatabaseServiceException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public DatabaseServiceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
    super(arg0, arg1, arg2, arg3);
  }

}

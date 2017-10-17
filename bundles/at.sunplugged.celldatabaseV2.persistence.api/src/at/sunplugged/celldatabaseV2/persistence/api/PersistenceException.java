package at.sunplugged.celldatabaseV2.persistence.api;

public class PersistenceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 224823677397877134L;

  public PersistenceException() {}

  public PersistenceException(String arg0) {
    super(arg0);
  }

  public PersistenceException(Throwable arg0) {
    super(arg0);
  }

  public PersistenceException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public PersistenceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
    super(arg0, arg1, arg2, arg3);
  }

}

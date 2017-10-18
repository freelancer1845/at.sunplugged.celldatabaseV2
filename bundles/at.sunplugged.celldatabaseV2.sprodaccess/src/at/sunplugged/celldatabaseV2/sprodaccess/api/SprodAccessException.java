package at.sunplugged.celldatabaseV2.sprodaccess.api;

public class SprodAccessException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 6032629825538695300L;

  public SprodAccessException() {}

  public SprodAccessException(String arg0) {
    super(arg0);
  }

  public SprodAccessException(Throwable arg0) {
    super(arg0);
  }

  public SprodAccessException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public SprodAccessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
    super(arg0, arg1, arg2, arg3);
  }

}

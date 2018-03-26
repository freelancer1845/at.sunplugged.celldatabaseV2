package at.sunplugged.celldatabasev2.logging;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class SWTLogAppender extends AppenderBase<ILoggingEvent> {

  private Display display;

  private Shell shell;

  private Level level = Level.ERROR;

  public SWTLogAppender() {
    System.out.println("Instantiated");

  }

  @Override
  protected void append(ILoggingEvent eventObject) {

    if (eventObject.getLevel().isGreaterOrEqual(level)) {
      if (checkDisplayAndShell() == false) {
        return;
      }

      display.asyncExec(() -> showMessage(eventObject));
    }

  }

  private void showMessage(ILoggingEvent eventObject) {
    MessageDialog.openError(shell, "Error", eventObject.getMessage());
  }

  private boolean checkDisplayAndShell() {
    Display display = Display.getDefault();
    if (display == null) {
      return false;
    }
    this.display = display;

    Shell shell = display.getActiveShell();
    if (shell == null) {
      return false;
    }
    this.shell = shell;

    return true;
  }
}

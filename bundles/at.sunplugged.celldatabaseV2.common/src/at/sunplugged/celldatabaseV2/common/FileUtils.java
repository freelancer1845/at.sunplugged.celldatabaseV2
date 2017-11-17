package at.sunplugged.celldatabaseV2.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

public class FileUtils {

  public static File locateRootFile(String path) throws IOException {
    File file = null;
    try {

      if (Platform.getInstallLocation() != null) {
        Path installPath = Paths.get((Platform.getInstallLocation()
            .getURL()
            .toURI()));
        Path filePath = Paths.get(installPath.toString(), path);
        if (filePath.toFile()
            .exists() == true) {
          file = filePath.toFile();
        }
      }
      if (file == null) {


        File bundleBase = FileLocator.getBundleFile(Activator.getContext()
            .getBundle());

        File basedirectory = bundleBase.getParentFile()
            .getParentFile();


        Path filePath = Paths.get(basedirectory.getAbsolutePath(),
            "features/at.sunplugged.celldatabaseV2.feature/rootfiles", path);
        if (filePath.toFile()
            .exists() == true) {
          file = filePath.toFile();
        }
      }
    } catch (Exception e) {
      throw new IOException("Failed to get root file...", e);
    }
    if (file != null) {
      return file;
    } else {
      throw new IOException("File not found...");
    }

  }

}

package at.sunplugged.celldatabaseV2.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.core.runtime.FileLocator;

public class FileUtils {

  public static File locateRootFile(String path) throws IOException {
    File file = null;
    Path filePath = null;
    try {
      File rootPath = new File("");

      filePath = Paths.get(rootPath.getAbsolutePath(), path);
      if (filePath.toFile()
          .exists()) {
        file = filePath.toFile();
      }

      if (file == null) {


        File bundleBase = FileLocator.getBundleFile(Activator.getContext()
            .getBundle());

        File basedirectory = bundleBase.getParentFile()
            .getParentFile();


        filePath = Paths.get(basedirectory.getAbsolutePath(),
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

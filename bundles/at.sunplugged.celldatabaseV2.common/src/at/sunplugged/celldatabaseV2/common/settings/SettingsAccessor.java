package at.sunplugged.celldatabaseV2.common.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SettingsAccessor {

  private static final Logger LOG = LoggerFactory.getLogger(SettingsAccessor.class);

  private static final String SETTING_FILE_NAME = "settings.json";

  private static SettingsAccessor instance = new SettingsAccessor();

  public static SettingsAccessor getInstance() {
    return instance;
  }

  private JSONSettings settings;

  public SettingsAccessor() {
    try {
      if (new File(SETTING_FILE_NAME).exists()) {
        readJsonSettings();
      } else {
        generateDefaultSettings();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public JSONSettings getSettings() {
    return this.settings;
  }

  public void flushSettings() throws IOException {
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    Gson gson = builder.create();

    try (FileWriter writer = new FileWriter(new File(SETTING_FILE_NAME))) {
      gson.toJson(settings, writer);
    }
  }

  public void flushSettingsIgnore() {
    try {
      flushSettings();
    } catch (IOException e) {
      LOG.debug("Failed to flush settings but ignored exception.", e);
    }
  }

  private void generateDefaultSettings() throws IOException {

    JSONSettings defaultSettings = JSONSettings.defaults();
    this.settings = defaultSettings;
    flushSettings();
  }

  private void readJsonSettings() throws FileNotFoundException, IOException {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    try (FileReader reader = new FileReader(SETTING_FILE_NAME)) {
      this.settings = gson.fromJson(reader, JSONSettings.class);
    }

  }

}

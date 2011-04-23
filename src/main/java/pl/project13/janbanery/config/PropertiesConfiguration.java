package pl.project13.janbanery.config;

import com.google.common.base.Strings;
import pl.project13.janbanery.config.auth.AuthMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Konrad Malawski
 */
public class PropertiesConfiguration extends DefaultConfiguration implements Configuration {

  private static final String DEFAULT_PROPS_FILENAME = "janbanery.properties";
  private static final String P_APIKEY               = "apikey";
  private static final String P_USERNAME             = "username";
  private static final String P_PASSWORD             = "password";

  private Properties properties = new Properties();

  /**
   * Setup the auth mode using the default properties file - {@link PropertiesConfiguration#DEFAULT_PROPS_FILENAME}
   * If the "apikey" key is present and not empty it will be used, otherwise the fallback
   * to user/pass mode is triggered. Please note that {@link PropertiesConfiguration} is NOT smart,
   * it will not try to fetch apiKey when configuring the app from a properties file and finding only user/pass data!
   *
   * @throws IOException if the file could not be found or read
   */
  public PropertiesConfiguration() throws IOException {
    this(DEFAULT_PROPS_FILENAME);
  }

  /**
   * Setup the auth mode using the properties file.
   * If the "apikey" key is present and not empty it will be used, otherwise the fallback
   * to user/pass mode is triggered. Please note that {@link PropertiesConfiguration} is NOT smart,
   * it will not try to fetch apiKey when configuring the app from a properties file and finding only user/pass data!
   *
   * @param propertiesFilename the filename (location) of the configuration properties file
   * @throws IOException if the file could not be found or read
   */
  public PropertiesConfiguration(String propertiesFilename) throws IOException {
    loadPropertiesFile(propertiesFilename);

    setupAuthMode();
  }

  /**
   * Setup the auth mode using the properties file.
   * If the "apikey" key is present and not empty it will be used, otherwise the fallback
   * to user/pass mode is triggered. Please note that {@link PropertiesConfiguration} is NOT smart,
   * it will not try to fetch apiKey when configuring the app from a properties file and finding only user/pass data!
   */
  private void setupAuthMode() {
    String apiKey = getStringProperty(P_APIKEY);
    if (Strings.isNullOrEmpty(apiKey)) {
      // try to use the user/pass mode instead
      forceUserPassAuthMode(getStringProperty(P_USERNAME), getStringProperty(P_PASSWORD));
    } else {
      // ok the key is present, let's use it
      forceKeyAuthMode(apiKey);
    }
  }

  private void loadPropertiesFile(String propertiesFilename) throws IOException {
    File propsFile = new File(propertiesFilename);
    if (doesNotExist(propsFile)) {
      throw new FileNotFoundException("Could not find '" + propsFile.getAbsolutePath() + "' file.");
    }
    FileReader reader = new FileReader(propsFile);

    properties.load(reader);
  }

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  private boolean doesNotExist(File propsFile) {
    return !propsFile.exists();
  }

  private String getStringProperty(String apikey) {
    return (String) properties.get(apikey);
  }
}


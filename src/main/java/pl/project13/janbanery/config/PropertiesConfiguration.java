package pl.project13.janbanery.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static pl.project13.janbanery.config.AuthMode.API_KEY_MODE;

/**
 * @author Konrad Malawski
 */
public class PropertiesConfiguration extends DefaultConfiguration implements Configuration {

  private static final String DEFAULT_PROPS_FILENAME = "janbanery.properties";
  private static final String P_APIKEY               = "apikey";
  private static final String P_USERNAME             = "username";
  private static final String P_PASSWORD             = "password";

  private Properties properties = new Properties();
  private AuthMode   authMode   = API_KEY_MODE;

  public PropertiesConfiguration() throws IOException {
    this(DEFAULT_PROPS_FILENAME);
  }

  public PropertiesConfiguration(String propertiesFilename) throws IOException {
    loadPropertiesFile(propertiesFilename);

    setupAuthMode();
  }

  private void setupAuthMode() {
    getStringProperty()
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
  public String getApiKey() {
    return getStringProperty(P_APIKEY);
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


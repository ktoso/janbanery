package pl.project13.janbanery.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static pl.project13.janbanery.config.AuthMode.API_KEY_MODE;
import static pl.project13.janbanery.config.AuthMode.USER_AND_PASS_MODE;

/**
 * @author Konrad Malawski
 */
public class PropertiesConfiguration implements Configuration {

  private static final String DEFAULT_PROPS_FILENAME = "janbanery.properties";
  private static final String APIKEY                 = "apikey";

  private Properties properties = new Properties();
  private AuthMode   authMode   = API_KEY_MODE;

  public PropertiesConfiguration() throws IOException {
    this(DEFAULT_PROPS_FILENAME);
  }

  public PropertiesConfiguration(String propertiesFilename) throws IOException {
    File propsFile = new File(propertiesFilename);
    if (doesNotExist(propsFile)) {
      throw new FileNotFoundException("Could not find '" + propsFile.getAbsolutePath() + "' file.");
    }
    FileReader reader = new FileReader(propsFile);

    properties.load(reader);
  }

  @Override
  public String getApiKey() {
    return getStringProperty(APIKEY);
  }

  @Override
  public void forceAuthMode(AuthMode authMode) {
    if (isChangeToApiKeyMode(authMode)) {
      // todo check for api key etc
      this.authMode = authMode;
    } else if (isChangeToUserAndPassMode(authMode)) {
      // todo check username is set etc
      this.authMode = authMode;
    }
  }

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  private boolean isChangeToUserAndPassMode(AuthMode newMode) {
    return authMode == API_KEY_MODE && newMode == USER_AND_PASS_MODE;
  }

  private boolean isChangeToApiKeyMode(AuthMode newMode) {
    return authMode == USER_AND_PASS_MODE && newMode == API_KEY_MODE;
  }

  private boolean doesNotExist(File propsFile) {
    return !propsFile.exists();
  }

  private String getStringProperty(String apikey) {
    return (String) properties.get(apikey);
  }
}


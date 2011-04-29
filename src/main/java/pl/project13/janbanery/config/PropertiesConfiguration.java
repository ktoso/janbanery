/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  public static final String DEFAULT_PROPS_FILENAME = "janbanery.properties";
  public static final String P_APIKEY               = "apikey";
  public static final String P_USERNAME             = "username";
  public static final String P_PASSWORD             = "password";

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


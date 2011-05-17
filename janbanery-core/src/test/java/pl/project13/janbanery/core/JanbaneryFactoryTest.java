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

package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.auth.ApiKeyAuthProvider;
import pl.project13.janbanery.config.auth.AuthProvider;
import pl.project13.janbanery.config.auth.UserPassAuthProvider;

import java.io.FileInputStream;
import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class JanbaneryFactoryTest {
  AsyncHttpClient asyncHttpClient;
  Configuration configuration;

  JanbaneryFactory janbaneryFactory;

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    asyncHttpClient = new AsyncHttpClient();

    janbaneryFactory = new JanbaneryFactory(asyncHttpClient);
  }

  @After
  public void tearDown() throws Exception {
    janbanery.close();
  }

  @Test
  public void shouldLoginUsingValidApiKey() throws Exception {
    // given
    configuration = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);

    // when
    janbanery = janbaneryFactory.connectUsing(configuration).toWorkspace(EXISTING_WORKSPACE);

    // then, should use API key mode
    AuthProvider usedAuthProvider = janbanery.getAuthMode();
    assertThat(usedAuthProvider).isInstanceOf(ApiKeyAuthProvider.class);
  }

  @Test
  public void shouldLoginUsingPlainApiKey() throws Exception {
    // given
    Properties props = new Properties();
    props.load(new FileInputStream(VALID_CONF_FILE_LOCATION));
    String apiKey = props.getProperty(PropertiesConfiguration.P_APIKEY);

    // when
    janbanery = janbaneryFactory.connectUsing(apiKey).toWorkspace(EXISTING_WORKSPACE);

    // then, should use API key mode
    AuthProvider usedAuthProvider = janbanery.getAuthMode();
    assertThat(usedAuthProvider).isInstanceOf(ApiKeyAuthProvider.class);
  }

  @Test
  public void shouldConnectAndKeepUsingUserPassMode() throws Exception {
    // given
    Properties properties = new Properties();
    properties.load(new FileInputStream(VALID_CONF_FILE_LOCATION));
    String user = (String) properties.get("username");
    String password = (String) properties.get("password");

    // when
    janbanery = janbaneryFactory.connectAndKeepUsing(user, password);

    // then, should use API key mode
    AuthProvider usedAuthProvider = janbanery.getAuthMode();
    assertThat(usedAuthProvider).isInstanceOf(UserPassAuthProvider.class);
  }

  @Test
  public void shouldLoginWithUserPassButThenFallbackToApiKeyMode() throws Exception {
    // given
    Properties properties = new Properties();
    properties.load(new FileInputStream(VALID_CONF_FILE_LOCATION));
    String user = (String) properties.get("username");
    String password = (String) properties.get("password");

    // when
    janbanery = janbaneryFactory.connectUsing(user, password).toWorkspace(EXISTING_WORKSPACE);

    // then, should use API key mode
    AuthProvider usedAuthProvider = janbanery.getAuthMode();
    assertThat(usedAuthProvider).isInstanceOf(ApiKeyAuthProvider.class);
  }

}

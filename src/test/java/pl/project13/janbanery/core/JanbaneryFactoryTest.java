package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.auth.ApiKeyAuthMode;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.auth.UserPassAuthMode;
import pl.project13.janbanery.test.TestConstants;

import java.io.FileInputStream;
import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class JanbaneryFactoryTest {
  AsyncHttpClient asyncHttpClient;
  Configuration   configuration;

  JanbaneryFactory janbaneryFactory;

  @Before
  public void setUp() throws Exception {
    asyncHttpClient = new AsyncHttpClient();

    janbaneryFactory = new JanbaneryFactory(asyncHttpClient);
  }

  @Test
  public void shouldLoginUsingValidApiKey() throws Exception {
    // given
    configuration = new PropertiesConfiguration(TestConstants.VALID_CONF_FILE_LOCATION);

    // when
    Janbanery janbanery = janbaneryFactory.connectUsing(configuration);

    // then, should use API key mode
    AuthMode usedAuthMode = janbanery.getAuthMode();
    assertThat(usedAuthMode).isInstanceOf(ApiKeyAuthMode.class);
  }

  @Test
  public void shouldLoginUsingPlainApiKey() throws Exception {
    // given
    Properties props = new Properties();
    props.load(new FileInputStream(TestConstants.VALID_CONF_FILE_LOCATION));
    String apiKey = props.getProperty(PropertiesConfiguration.P_APIKEY);

    // when
    Janbanery janbanery = janbaneryFactory.connectUsing(apiKey);

    // then, should use API key mode
    AuthMode usedAuthMode = janbanery.getAuthMode();
    assertThat(usedAuthMode).isInstanceOf(ApiKeyAuthMode.class);
  }

  @Test
  public void shouldConnectAndKeepUsingUserPassMode() throws Exception {
    // given
    Properties properties = new Properties();
    properties.load(new FileInputStream(TestConstants.VALID_CONF_FILE_LOCATION));
    String user = (String) properties.get("username");
    String password = (String) properties.get("password");

    // when
    Janbanery janbanery = janbaneryFactory.connectAndKeepUsing(user, password);

    // then, should use API key mode
    AuthMode usedAuthMode = janbanery.getAuthMode();
    assertThat(usedAuthMode).isInstanceOf(UserPassAuthMode.class);
  }

  @Test
  public void shouldLoginWithUserPassButThenFallbackToApiKeyMode() throws Exception {
    // given
    Properties properties = new Properties();
    properties.load(new FileInputStream(TestConstants.VALID_CONF_FILE_LOCATION));
    String user = (String) properties.get("username");
    String password = (String) properties.get("password");

    // when
    Janbanery janbanery = janbaneryFactory.connectUsing(user, password);

    // then, should use API key mode
    AuthMode usedAuthMode = janbanery.getAuthMode();
    assertThat(usedAuthMode).isInstanceOf(ApiKeyAuthMode.class);
  }

}

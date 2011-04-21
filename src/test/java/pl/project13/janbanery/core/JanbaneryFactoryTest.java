package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.AuthMode;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.test.TestConstants;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.config.AuthMode.*;

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
    JanbaneryImpl janbanery = janbaneryFactory.connectUsing(configuration);

    // then, should use API key mode
    AuthMode usedAuthMode = janbanery.getAuthMode();
    assertThat(usedAuthMode).isEqualTo(API_KEY_MODE);
  }

}

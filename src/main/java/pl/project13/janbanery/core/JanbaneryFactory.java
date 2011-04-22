package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.DefaultConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class JanbaneryFactory {

  private Logger log = LoggerFactory.getLogger(getClass());

  private AsyncHttpClient asyncHttpClient;

  public JanbaneryFactory() {
    asyncHttpClient = new AsyncHttpClient();
  }

  public JanbaneryFactory(AsyncHttpClient asyncHttpClient) {
    this.asyncHttpClient = asyncHttpClient;
  }

  public JanbaneryImpl connectUsing(Configuration configuration) {
    return new JanbaneryImpl(configuration, asyncHttpClient, GsonFactory.create());
  }

  public JanbaneryImpl connectUsing(String apiKey) {
    DefaultConfiguration conf = new DefaultConfiguration(apiKey);
    return connectUsing(conf);
  }

}


package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.DefaultConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.resources.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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

  public Janbanery connectUsing(Configuration configuration) {
    return new Janbanery(configuration, asyncHttpClient, GsonFactory.create());
  }

  /**
   * The <strong>recommended</strong> method for connecting to Kanbanery.
   *
   * @param apiKey your API key for kanbanery, you can find it in your profile settings on kanbanery.com
   * @return a properly setup Janbanery instance using your API key for authentication
   */
  public Janbanery connectUsing(String apiKey) {
    DefaultConfiguration conf = new DefaultConfiguration(apiKey);
    return connectUsing(conf);
  }

  /**
   * This method will connect to kanbanery via basic user/pass authentication
   * <strong>but will then switch to API Key Mode</strong> to increase the security of communication over the wire.
   *
   * @param user     your kanbanery username (email)
   * @param password your kanbanery password for this user
   * @return an Janbanery instance setup using the API Key auth, which will be fetched during construction
   * @throws IOException
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public Janbanery connectUsing(String user, String password) throws IOException, ExecutionException, InterruptedException {
    DefaultConfiguration conf = new DefaultConfiguration(user, password);
    Janbanery janbanery = new Janbanery(conf, asyncHttpClient, GsonFactory.create());

    // fetch and switch to apiKey mode
    String apiKey = getCurrentUserApiKey(janbanery);
    conf.forceKeyAuthMode(apiKey);

    return janbanery;
  }

  private String getCurrentUserApiKey(Janbanery janbanery) throws IOException, ExecutionException, InterruptedException {
    User currentUser = janbanery.users().current();
    return currentUser.getApiToken();
  }

  /**
   * This method will connect to kanbanery via basic user/pass authentication
   * <strong>and will keep using it until forced to switch modes!</strong>. This method is not encouraged,
   * you should use {@link JanbaneryFactory#connectUsing(String, String)} and allow Janbanery to switch to apiKey mode
   * as soon as it load's up to increase security over the wire.
   *
   * @param user     your kanbanery username (email)
   * @param password your kanbanery password for this user
   * @return an Janbanery instance setup using the API Key auth, which will be fetched during construction
   * @throws IOException
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public Janbanery connectAndKeepUsing(String user, String password) throws IOException, ExecutionException, InterruptedException {
    DefaultConfiguration conf = new DefaultConfiguration(user, password);
    return new Janbanery(conf, asyncHttpClient, GsonFactory.create());
  }

}


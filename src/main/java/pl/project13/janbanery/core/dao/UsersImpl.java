package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Konrad Malawski
 */
public class UsersImpl implements Users {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration   conf;
  private Gson            gson;
  private AsyncHttpClient asyncHttpClient;
  private Workspace       currentWorkspace;

  public UsersImpl(Configuration conf, Gson gson, AsyncHttpClient asyncHttpClient) {
    this.conf = conf;
    this.gson = gson;
    this.asyncHttpClient = asyncHttpClient;
  }

  @Override
  public User current() throws IOException, ExecutionException, InterruptedException {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
        .prepareGet(conf.getApiUrl() + "workspaces.json");
    requestBuilder = conf.authorize(requestBuilder);

    Future<Response> futureResponse = requestBuilder.execute();

    Response response = futureResponse.get();
//    asyncHttpClient.close();

    String responseBody = response.getResponseBody();
    log.info("Fetched response: {}", responseBody);

    List<User> users = gson.fromJson(responseBody, GsonTypeTokens.LIST_USER);
    assert users.size() == 1;
    return users.get(0); // todo this will fail probably, must search via apikey or login!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  }

  public Users using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;
    return this;
  }
}


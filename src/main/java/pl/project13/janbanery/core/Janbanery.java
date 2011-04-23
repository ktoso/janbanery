package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Janbanery {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration   conf;
  private AsyncHttpClient asyncHttpClient;
  private Gson            gson;

  /**
   * The workspace on which all calls will be performed
   */
  private String currentWorkspace = "";

  public Janbanery(Configuration conf) {
    this(conf, new AsyncHttpClient(), GsonFactory.create());
  }

  public Janbanery(Configuration conf, AsyncHttpClient asyncHttpClient, Gson gson) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.gson = gson;
  }

  public AuthMode getAuthMode() {
    return conf.getAuthMode();
  }

  public List<Workspace> findAllWorkspaces() throws IOException, ExecutionException, InterruptedException {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
        .prepareGet(conf.getApiUrl() + "workspaces.json");
    conf.authorize(requestBuilder);

    Future<Response> futureResponse = requestBuilder.execute();

    Response response = futureResponse.get();
    asyncHttpClient.close();

    String responseBody = response.getResponseBody();
    log.info("Fetched response: {}", responseBody);

    List<Workspace> workspaces = gson.fromJson(responseBody, GsonTypeTokens.LIST_WORKSPACES);
    assert workspaces != null : "Workspaces should not be null here";
    return workspaces;
  }

  public User currentUser() throws IOException, ExecutionException, InterruptedException {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
        .prepareGet(conf.getApiUrl() + "workspaces.json");
    requestBuilder = conf.authorize(requestBuilder);

    Future<Response> futureResponse = requestBuilder.execute();

    Response response = futureResponse.get();
    asyncHttpClient.close();

    String responseBody = response.getResponseBody();
    log.info("Fetched response: {}", responseBody);

    List<User> users = gson.fromJson(responseBody, GsonTypeTokens.LIST_USER);
    assert users.size() == 1;
    return users.get(0);
  }
}

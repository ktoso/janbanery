package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.core.dao.TasksImpl;
import pl.project13.janbanery.core.dao.Users;
import pl.project13.janbanery.core.dao.UsersImpl;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
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
  private Workspace currentWorkspace;

  public Janbanery(Configuration conf, AsyncHttpClient asyncHttpClient, Gson gson) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.gson = gson;
  }

  public AuthMode getAuthMode() {
    return conf.getAuthMode();
  }

  public List<Workspace> workspaces() throws IOException, ExecutionException, InterruptedException {
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

  public Workspace workspace(String name) throws IOException, ExecutionException, InterruptedException {
    for (Workspace workspace : workspaces()) {
      if (workspace.getName().equals(name)) {
        return workspace;
      }
    }
    throw new EntityNotFoundException("Could not find workspace with name");
  }

  /**
   * Delegates to {@link Janbanery#workspace(String)} and keeps this workspace as the default one.
   * From now on, all calls requiring a workspace will use this workspace, you may change the default workspace
   * at anytime by calling this method again.
   *
   * @param name the name of the workspace to be used
   * @return the fetched workspace
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public Workspace usingWorkspace(String name) throws IOException, ExecutionException, InterruptedException {
    currentWorkspace = workspace(name);
    return currentWorkspace;
  }

  /* return initially setup instances of dao objects */

  public Tasks tasks() {
    return new TasksImpl(conf, gson, asyncHttpClient).usingWorkspace(currentWorkspace);
  }

  public Users users() {
    return new UsersImpl(conf, gson, asyncHttpClient).usingWorkspace(currentWorkspace);
  }
}

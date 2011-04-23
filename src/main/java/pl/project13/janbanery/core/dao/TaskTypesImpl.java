package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public class TaskTypesImpl implements TaskTypes {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration   conf;
  private Gson            gson;
  private AsyncHttpClient asyncHttpClient;

  private Workspace currentWorkspace;
  private Project   currentProject;

  public TaskTypesImpl(Configuration conf, Gson gson, AsyncHttpClient asyncHttpClient) {
    this.conf = conf;
    this.gson = gson;
    this.asyncHttpClient = asyncHttpClient;
  }

  @Override
  public List<TaskType> all() throws ExecutionException, InterruptedException, IOException {
    String url = conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "task_types.json"; // todo externalize better, it's a resource url after all
    log.info("Calling GET on: " + url);

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(url);
    conf.authorize(requestBuilder);

    // todo refactor this
    ListenableFuture<Response> futureResponse = requestBuilder.execute();
    Response response = futureResponse.get();
    String responseBody = response.getResponseBody();

    RestClient.verifyResponseCode(response);

    log.info("Got response for creating task: {}", responseBody);

    List<TaskType> taskTypes = gson.fromJson(responseBody, GsonTypeTokens.LIST_TASK_TYPE);
    assert taskTypes != null;
    return taskTypes;
  }

  @Override
  public TaskType any() throws IOException, ExecutionException, InterruptedException {
    return all().get(0);
  }

  /**
   * Set this instance to fallback to using the passed in workspace, when none other is passed explicitly.
   * Using this once means you don't have to pass this workspace instance around any longer when performing
   * actions on these tasks.
   *
   * @param currentWorkspace the workspace to be used in all consequent requests
   * @param currentProject   the project to be used in all consequent requests
   * @return the same instance of {@link TasksImpl} as before, but setup to use the currentWorkspace
   */
  public TaskTypesImpl using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}

package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.encoders.FormUrlEncodedBodyGenerator;
import pl.project13.janbanery.resources.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public class TasksImpl implements Tasks {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration               conf;
  private Gson                        gson;
  private AsyncHttpClient             asyncHttpClient;
  private FormUrlEncodedBodyGenerator formUrlEncodedBodyGenerator;

  private Workspace currentWorkspace;
  private Project   currentProject;

  public TasksImpl(Configuration conf, Gson gson, AsyncHttpClient asyncHttpClient, FormUrlEncodedBodyGenerator formUrlEncodedBodyGenerator) {
    this.conf = conf;
    this.gson = gson;
    this.asyncHttpClient = asyncHttpClient;
    this.formUrlEncodedBodyGenerator = formUrlEncodedBodyGenerator;
  }

  @Override
  public void create(Task task) throws IOException, ExecutionException, InterruptedException {
    String url = conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "tasks.json"; // todo externalize better, it's a resource url after all
    log.info("Calling POST on: " + url);

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePost(url);
    conf.authorize(requestBuilder);

    // todo refactor this
    String taskField = "task[%s]";
    requestBuilder.setBody(formUrlEncodedBodyGenerator.asString(task));

    ListenableFuture<Response> futureResponse = requestBuilder.execute();
    Response response = futureResponse.get();
    String responseBody = response.getResponseBody();

    RestClient.verifyResponseCode(response);

    log.info("Got response for creating task: {}", responseBody);
  }

  @Override
  public void createInIcebox(Task task) {
    // todo implement me
  }

  @Override
  public TaskMoveFlow move(Task task) {
    return new TaskMoveFlow(conf, asyncHttpClient, task);
  }

  @Override
  public List<Task> all() {
    return null;  // todo implement me.
  }

  @Override
  public List<Task> assignedToMe() {
    return null;  // todo implement me.
  }

  @Override
  public List<Task> assignedTo(User user) {
    return null;  // todo implement me.
  }

  @Override
  public List<Task> withPriority(Priority priority) {
    return null;  // todo implement me.
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
  public TasksImpl using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}

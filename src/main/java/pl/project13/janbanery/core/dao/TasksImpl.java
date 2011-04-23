package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class TasksImpl implements Tasks {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration   conf;
  private Gson            gson;
  private AsyncHttpClient asyncHttpClient;
  private Workspace       currentWorkspace;

  public TasksImpl(Configuration conf, Gson gson, AsyncHttpClient asyncHttpClient) {
    this.conf = conf;
    this.gson = gson;
    this.asyncHttpClient = asyncHttpClient;
  }

  @Override
  public void create(Task task) {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePost(conf.getApiUrl() + "tasks.json");
    conf.authorize(requestBuilder);
  }

  @Override
  public void createInIcebox(Task task) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public TaskMoveFlow move(Task task) {
    return new TaskMoveFlow(this, task);
  }

  @Override
  public List<Task> all() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<Task> assignedToMe() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<Task> assignedTo(User user) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<Task> withPriority(Priority priority) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * Set this instance to fallback to using the passed in workspace, when none other is passed explicitly.
   * Using this once means you don't have to pass this workspace instance around any longer when performing
   * actions on these tasks.
   *
   * @param currentWorkspace the workspace to be used in all consequent requests
   * @return the same instance of {@link TasksImpl} as before, but setup to use the currentWorkspace
   */
  public TasksImpl usingWorkspace(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;
    return this;
  }
}

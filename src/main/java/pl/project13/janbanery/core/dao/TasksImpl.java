package pl.project13.janbanery.core.dao;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.core.flow.TaskFlowImpl;
import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.core.flow.TaskMoveFlowImpl;
import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.resources.*;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class TasksImpl implements Tasks {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient    restClient;
  private Gson          gson;

  private Workspace currentWorkspace;
  private Project   currentProject;

  public TasksImpl(Configuration conf, RestClient restClient, Gson gson) {
    this.conf = conf;
    this.restClient = restClient;
    this.gson = gson;
  }

  @Override
  public TaskFlow create(Task task) throws IOException, ExecutionException, InterruptedException {
    String url = getDefaultGetUrl(); // todo externalize better, it's a resource url after all
    log.info("Calling POST on: " + url);

    String responseBody = restClient.doPost(task, url, this);

    Task newTask = gson.fromJson(responseBody, GsonTypeTokens.TASK);
    return new TaskFlowImpl(this, newTask);
  }

  @Override
  public void delete(Task task) throws IOException {
    String url = getDeleteUrl(task);
    restClient.doDelete(url);
  }

  @Override
  public TaskFlow createInIcebox(Task task) throws IOException {
    throw new NotYetImplementedException();
  }

  @Override
  public void archive(Task task) throws IOException, ExecutionException, InterruptedException {
    throw new NotYetImplementedException();
  }

  @Override
  public void archiveAllInLastColumn() throws IOException {
    throw new NotYetImplementedException();
  }

  @Override
  public TaskMoveFlow move(Task task) {
    return new TaskMoveFlowImpl(this, task);
  }

  @Override
  public TaskFlow move(Task task, TaskLocation location) {
    return null; // todo implement me
  }

  @Override
  public TaskMoveFlow markNotReadyToPull(Task task) {
    return null;  // todo implement me.
  }

  @Override
  public TaskMoveFlow markReadyToPull(Task task) {
    return null;  // todo implement me.
  }

  @Override
  // todo port this to *Flow
  public List<Task> all() throws IOException {
    String url = getDefaultGetUrl();

    Response response = restClient.doGet(url);
    String responseBody = response.getResponseBody();

    return gson.fromJson(responseBody, GsonTypeTokens.LIST_TASK);
  }

  @Override
  public Task byId(Long taskId) throws IOException {
    String url = getDefaultGetUrl();
    Response response = restClient.doGet(url);
    String responseBody = response.getResponseBody();

    return gson.fromJson(responseBody, GsonTypeTokens.TASK);
  }

  @Override
  public List<Task> byTitle(String taskTitle) throws IOException {
    List<Task> tasks = all();
    Collection<Task> filteredTasks = filter(tasks, new TaskByTitlePredicate(taskTitle));
    return newArrayList(filteredTasks);
  }

  @Override
  public List<Task> byTitleIgnoreCase(String taskTitle) throws IOException {
    List<Task> tasks = all();
    Collection<Task> filteredTasks = filter(tasks, new TaskByTitleIgnoreCasePredicate(taskTitle));
    return newArrayList(filteredTasks);
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

  /**
   * The url prepared for most GET requests on this resource,
   * it looks like: https://WORKSPACE.kanbanery.com/api/v1/projects/PROJECT_ID/tasks.json
   *
   * @return url string prepared for querying for data
   */
  private String getDefaultGetUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "tasks.json";
  }

  private String getDeleteUrl(Task task) {
    return conf.getApiUrl(currentWorkspace.getName(), task);
  }

  // ------------------------- inner predicate classes ------------------------

  private static class TaskByTitlePredicate implements Predicate<Task> {

    private String taskTitle;

    public TaskByTitlePredicate(String taskTitle) {
      this.taskTitle = taskTitle;
    }

    @Override
    public boolean apply(Task input) {
      return taskTitle.equals(input.getTitle());
    }
  }

  private static class TaskByTitleIgnoreCasePredicate implements Predicate<Task> {

    private String taskTitle;

    public TaskByTitleIgnoreCasePredicate(String taskTitle) {
      this.taskTitle = taskTitle;
    }

    @Override
    public boolean apply(Task input) {
      return taskTitle.equalsIgnoreCase(input.getTitle());
    }
  }

}

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
import pl.project13.janbanery.exceptions.kanbanery.CanNotDeleteNotEmptyColumnException;
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
    String url = getDefaultGetUrl();
    log.info("Calling POST on: " + url);

    String responseBody = restClient.doPost(task, url);

    Task newTask = gson.fromJson(responseBody, GsonTypeTokens.TASK);
    return new TaskFlowImpl(this, newTask);
  }

  @Override
  public void delete(Task task) throws IOException {
    String url = getTaskUrl(task);
    restClient.doDelete(url);
  }

  @Override
  public TaskFlow createInIcebox(Task task) throws IOException {
    throw new NotYetImplementedException(); // todo
  }

  @Override
  public void archive(Task task) throws IOException, ExecutionException, InterruptedException {
    throw new NotYetImplementedException(); // todo
  }

  @Override
  public void archiveAllInLastColumn() throws IOException, CanNotDeleteNotEmptyColumnException {
    throw new NotYetImplementedException(); // todo
  }

  @Override
  public TaskMoveFlow move(Task task) {
    return new TaskMoveFlowImpl(this, task);
  }

  @Override
  public TaskFlow move(Task task, TaskLocation location) throws IOException {
    String url = getTaskUrl(task);
    String moveRequest = location.requestBody();

    Task movedTask = restClient.doPut(url, moveRequest, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, movedTask);
  }

  @Override
  public TaskFlow markNotReadyToPull(Task task) {
    throw new NotYetImplementedException(); // todo
  }

  @Override
  public TaskFlow markReadyToPull(Task task) {
    throw new NotYetImplementedException(); // todo
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
    throw new NotYetImplementedException(); // todo
  }

  @Override
  public List<Task> assignedTo(User user) {
    throw new NotYetImplementedException(); // todo
  }

  @Override
  public List<Task> withPriority(Priority priority) {
    throw new NotYetImplementedException(); // todo
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
   * It looks like: https://WORKSPACE.kanbanery.com/api/v1/projects/PROJECT_ID/tasks.json
   *
   * @return url string prepared for querying for data
   */
  private String getDefaultGetUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "tasks.json";
  }

  /**
   * Returns a proper url to call API calls for this task on -
   * for PUT/DELETE calls.
   * <p/>
   * It looks like: https://WORKSPACE.kanbanery.com/api/v1/tasks/TASK_ID.json
   *
   * @param task the task for which to generate the url
   * @return the proper URL for API calls on this task
   */
  private String getTaskUrl(Task task) {
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

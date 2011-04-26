package pl.project13.janbanery.core.dao;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.core.flow.*;
import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.exceptions.kanbanery.CanNotDeleteNotEmptyColumnException;
import pl.project13.janbanery.resources.*;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow create(Task task) throws IOException {
    String url = getDefaultGetUrl();

    Task newTask = restClient.doPost(url, task, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, newTask);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Task task) throws IOException {
    String url = getTaskUrl(task);

    restClient.doDelete(url);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void archive(Task task) throws IOException {
    throw new NotYetImplementedException(); // todo
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void archiveAllInLastColumn() throws IOException, CanNotDeleteNotEmptyColumnException {
    throw new NotYetImplementedException(); // todo
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow move(Task task) {
    return new TaskMoveFlowImpl(this, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow move(Task task, TaskLocation location) throws IOException {
    String url = getTaskUrl(task);
    String moveRequest = location.requestBody();

    Task movedTask = restClient.doPut(url, moveRequest, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, movedTask);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow move(Task task, Column column) throws IOException {
    String url = getTaskUrl(task);
    Task requestObject = new Task();
    requestObject.setColumnId(column.getId());

    Task movedTask = restClient.doPut(url, requestObject, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, movedTask);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow update(Task task, Task newValues) throws IOException {
    String url = getTaskUrl(task);

    Task newTask = restClient.doPut(url, newValues, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, newTask);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMarkFlow mark(Task task) {
    return new TaskMarkFlowImpl(this, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow markAsReadyToPull(Task task) throws IOException {
    Task commandObject = new Task();
    commandObject.setReadyToPull(true);

    return update(task, commandObject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow markAsNotReadyToPull(Task task) throws IOException {
    Task commandObject = new Task();
    commandObject.setReadyToPull(false);

    return update(task, commandObject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> all() throws IOException {
    // todo port this to *Flow
    String url = getDefaultGetUrl();

    Response response = restClient.doGet(url);
    String responseBody = response.getResponseBody();

    return gson.fromJson(responseBody, GsonTypeTokens.LIST_TASK);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> allIn(Column column) throws IOException {
    String url = getColumnTasksUrl(column.getId());
    return restClient.doGet(url, GsonTypeTokens.LIST_TASK);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Task byId(Long taskId) throws IOException {
    String url = getDefaultGetUrl();
    Response response = restClient.doGet(url);
    String responseBody = response.getResponseBody();

    return gson.fromJson(responseBody, GsonTypeTokens.TASK);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> byTitle(String taskTitle) throws IOException {
    List<Task> tasks = all();
    Collection<Task> filteredTasks = filter(tasks, new TaskByTitlePredicate(taskTitle));
    return newArrayList(filteredTasks);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> byTitleIgnoreCase(String taskTitle) throws IOException {
    List<Task> tasks = all();
    Collection<Task> filteredTasks = filter(tasks, new TaskByTitleIgnoreCasePredicate(taskTitle));
    return newArrayList(filteredTasks);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> assignedToMe() {
    throw new NotYetImplementedException(); // todo
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> assignedTo(User user) throws IOException {
    List<Task> all = all();
    Collection<Task> filteredTasks = filter(all, new TaskByOwnerPredicate(user));
    return newArrayList(filteredTasks);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> withPriority(Priority priority) throws IOException {
    List<Task> all = all();
    Collection<Task> filteredTasks = filter(all, new TaskByPriorityPredicate(priority));
    return newArrayList(filteredTasks);
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
   * @param task the task from which we take the id to work on
   * @return the proper URL for API calls on this task
   */
  private String getTaskUrl(Task task) {
    return conf.getApiUrl(currentWorkspace.getName(), "tasks", task.getId());
  }

  /**
   * Returns a proper url to call API calls for this task on -
   * for PUT/DELETE calls.
   * <p/>
   * It looks like: https://WORKSPACE.kanbanery.com/api/v1/tasks/TASK_ID.json
   *
   * @param columnId id of the column of which we want the lasts
   * @return the proper URL for API calls on this task
   */
  private String getColumnTasksUrl(Long columnId) {
    return conf.getApiUrl(currentWorkspace.getName()) + "columns/" + columnId + "/tasks.json";
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

  private static class TaskByOwnerPredicate implements Predicate<Task> {
    private Long userId;

    public TaskByOwnerPredicate(User user) {
      this.userId = user.getId();
    }

    @Override
    public boolean apply(Task task) {
      return task.getOwnerId().equals(userId);
    }
  }

  private static class TaskByPriorityPredicate implements Predicate<Task> {
    private Priority priority;

    public TaskByPriorityPredicate(Priority priority) {
      this.priority = priority;
    }

    @Override
    public boolean apply(Task task) {
      return task.getPriority() == priority;
    }
  }
}

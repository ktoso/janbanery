/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.core.flow.*;
import pl.project13.janbanery.core.flow.batch.TasksMoveAllFlow;
import pl.project13.janbanery.core.flow.batch.TasksMoveAllFlowImpl;
import pl.project13.janbanery.resources.*;
import pl.project13.janbanery.resources.additions.TaskLocation;
import pl.project13.janbanery.util.predicates.TaskByOwnerPredicate;
import pl.project13.janbanery.util.predicates.TaskByPriorityPredicate;
import pl.project13.janbanery.util.predicates.TaskByTitleIgnoreCasePredicate;
import pl.project13.janbanery.util.predicates.TaskByTitlePredicate;

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
  private RestClient restClient;

  private Columns columns;

  private Workspace currentWorkspace;
  private Project currentProject;

  public TasksImpl(Columns columns, Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
    this.columns = columns;
  }

  @Override
  public TaskFlow create(Task task) throws IOException {
    String url = getDefaultGetUrl();

    Task newTask = restClient.doPost(url, task, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, columns, newTask);
  }

  @Override
  public void delete(Task task) throws IOException {
    String url = getTaskUrl(task);

    restClient.doDelete(url);
  }

  @Override
  public TaskMoveFlow move(Task task) {
    return new TaskMoveFlowImpl(this, columns, task);
  }

  @Override
  public TasksMoveAllFlow moveAllFrom(Column column) throws IOException {
    List<Task> tasks = allIn(column);
    return new TasksMoveAllFlowImpl(this, tasks);
  }

  @Override
  public void moveAll(Column srcColumn, Column destColumn) throws IOException {
    List<Task> tasks = allIn(srcColumn);
    for (Task task : tasks) {
      move(task, destColumn);
    }

  }

  @Override
  public TaskFlow move(Task task, TaskLocation location) throws IOException {
    String url = getTaskUrl(task);
    String moveRequest = location.requestBody();

    Task movedTask = restClient.doPut(url, moveRequest, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, columns, movedTask);
  }

  @Override
  public TaskFlow move(Task task, Column column) throws IOException {
    String url = getTaskUrl(task);
    Task commandObject = new Task();
    commandObject.setColumnId(column.getId());
    commandObject.setPosition(1); // or else it will throw an exception...

    Task movedTask = restClient.doPut(url, commandObject, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, columns, movedTask);
  }

  @Override
  public TaskAssignmentFlow assign(Task task) {
    return new TaskAssignmentFlowImpl(this, task);
  }

  @Override
  public TaskFlow assign(Task task, User user) throws IOException {
    String url = getTaskUrl(task);
    String command = task.getResourceId() + "[owner_id]=%s";

    if (user == null) {
      command = String.format(command, "");
    } else {
      command = String.format(command, user.getId());
    }

    Task assignedTask = restClient.doPut(url, command, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, columns, assignedTask);
  }

  @Override
  public TaskUpdateFlow update(Task task) {
    return new TaskUpdateFlowImpl(this, columns, task);
  }

  @Override
  public TaskFlow update(Task task, Task newValues) throws IOException {
    String url = getTaskUrl(task);

    Task newTask = restClient.doPut(url, newValues, GsonTypeTokens.TASK);

    return new TaskFlowImpl(this, columns, newTask);
  }

  @Override
  public TaskMarkFlow mark(Task task) {
    return new TaskMarkFlowImpl(this, columns, task);
  }

  @Override
  public TaskFlow markAsReadyToPull(Task task) throws IOException {
    Task commandObject = new Task();
    commandObject.setReadyToPull(true);

    return update(task, commandObject);
  }

  @Override
  public TaskFlow markAsNotReadyToPull(Task task) throws IOException {
    Task commandObject = new Task();
    commandObject.setReadyToPull(false);

    return update(task, commandObject);
  }

  @Override
  public List<Task> all() throws IOException {
    // todo port this to *Flow
    String url = getDefaultGetUrl();

    List<Task> tasks = restClient.doGet(url, GsonTypeTokens.LIST_TASK);

    return tasks;
  }

  @Override
  public List<Task> allIn(Column column) throws IOException {
    String url = getColumnTasksUrl(column.getId());

    List<Task> tasks = restClient.doGet(url, GsonTypeTokens.LIST_TASK);

    return tasks;
  }

  @Override
  public Task refresh(Task task) throws IOException {
    return byId(task.getId());
  }

  @Override
  public Task byId(Long taskId) throws IOException {
    String url = getTaskUrl(taskId);

    Task task = restClient.doGet(url, GsonTypeTokens.TASK);

    return task;
  }

  @Override
  public List<Task> allByTitle(String taskTitle) throws IOException {
    List<Task> tasks = all();
    Collection<Task> filteredTasks = filter(tasks, new TaskByTitlePredicate(taskTitle));
    return newArrayList(filteredTasks);
  }

  @Override
  public List<Task> allByTitleIgnoreCase(String taskTitle) throws IOException {
    List<Task> tasks = all();
    Collection<Task> filteredTasks = filter(tasks, new TaskByTitleIgnoreCasePredicate(taskTitle));
    return newArrayList(filteredTasks);
  }

  @Override
  public List<Task> allAssignedTo(User user) throws IOException {
    List<Task> all = all();
    Collection<Task> filteredTasks = filter(all, new TaskByOwnerPredicate(user));
    return newArrayList(filteredTasks);
  }

  @Override
  public List<Task> allWithPriority(Priority priority) throws IOException {
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
    return conf.getApiUrl(currentWorkspace, currentProject.getId()) + "tasks.json";
  }

  private String getTaskUrl(Long taskId) {
    return conf.getApiUrl(currentWorkspace, "tasks", taskId);
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
    return getTaskUrl(task.getId());
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
    return conf.getApiUrl(currentWorkspace) + "columns/" + columnId + "/tasks.json";
  }

}

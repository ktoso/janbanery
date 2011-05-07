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

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.core.flow.SubTaskFlow;
import pl.project13.janbanery.core.flow.SubTaskFlowImpl;
import pl.project13.janbanery.core.flow.batch.SubTasksFlow;
import pl.project13.janbanery.core.flow.batch.SubTasksFlowImpl;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.util.predicates.CompletedSubTaskPredicate;
import pl.project13.janbanery.util.predicates.NotCompletedSubTaskPredicate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.*;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class SubTasksImpl implements SubTasks {

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;

  public SubTasksImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public SubTaskFlow create(Task task, SubTask subTask) throws IOException {
    String url = getSubTasksUrl(task);

    SubTask createdSubTask = restClient.doPost(url, subTask, GsonTypeTokens.SUB_TASK);

    return new SubTaskFlowImpl(this, createdSubTask);
  }

  @Override
  public SubTaskFlow update(SubTask subTask, SubTask newValues) throws IOException {
    String url = getSubTaskUrl(subTask);

    SubTask updatedTask = restClient.doPut(url, newValues, GsonTypeTokens.SUB_TASK);

    return new SubTaskFlowImpl(this, updatedTask);
  }

  @Override
  public void delete(SubTask subTask) {
    String url = getSubTaskUrl(subTask);

    restClient.doDelete(url);
  }

  @Override
  public List<SubTask> all(Task task) throws IOException {
    String url = getSubTasksUrl(task);

    return restClient.doGet(url, GsonTypeTokens.LIST_SUB_TASK);
  }

  @Override
  public List<SubTask> allNotCompleted(Task task) throws IOException {
    Collection<SubTask> completedSubTasks = filter(all(task), new NotCompletedSubTaskPredicate());

    return newArrayList(completedSubTasks);
  }

  @Override
  public List<SubTask> allCompleted(Task task) throws IOException {
    Collection<SubTask> completedSubTasks = filter(all(task), new CompletedSubTaskPredicate());

    return newArrayList(completedSubTasks);
  }

  private String getSubTaskUrl(SubTask subTask) {
    return conf.getApiUrl(currentWorkspace, "subtasks", subTask.getId());
  }

  private String getSubTasksUrl(Task task) {
    return conf.getApiUrl(currentWorkspace, "tasks", task.getId(), "subtasks");
  }

  public SubTasksImpl using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;

    return this;
  }

  @Override
  public SubTasksFlow of(Task task) {
    return new SubTasksFlowImpl(this, task);
  }

}

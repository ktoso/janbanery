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
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class SubTasksImpl implements SubTasks {
  private Tasks         tasks;
  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;
  private Project   currentProject;

  private Task task;

  public SubTasksImpl(Tasks tasks, Task task, Configuration conf, RestClient restClient) {
    this.tasks = tasks;
    this.task = task;
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public SubTaskFlow create() {
    return null;  // todo implement me.
  }

  @Override
  public void delete() {

  }

  @Override
  public List<SubTask> all() throws IOException {
    String url = getSubTasksUrl(task);

    return restClient.doGet(url, GsonTypeTokens.LIST_SUB_TASK);
  }

  private String getSubTasksUrl(Task task) {
    return conf.getApiUrl(currentWorkspace, "tasks", task.getId(), "subtasks");
  }

  public SubTasks using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;

    return this;
  }
}

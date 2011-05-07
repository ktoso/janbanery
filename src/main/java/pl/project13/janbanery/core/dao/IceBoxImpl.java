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
import pl.project13.janbanery.core.flow.IceBoxFlow;
import pl.project13.janbanery.core.flow.IceBoxFlowImpl;
import pl.project13.janbanery.core.flow.TaskUpdateFlow;
import pl.project13.janbanery.core.flow.TaskUpdateFlowImpl;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class IceBoxImpl implements IceBox {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks tasks;
  private Columns columns;

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  public IceBoxImpl(Tasks tasks, Columns columns, Configuration conf, RestClient restClient) {
    this.tasks = tasks;
    this.columns = columns;
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public IceBoxFlow create(Task task) throws IOException {
    String url = getDefaultUrl();

    Task newTask = restClient.doPost(url, task, GsonTypeTokens.TASK);

    return new IceBoxFlowImpl(tasks, this, newTask);
  }

  @Override
  public void delete(Task task) throws IOException {
    tasks.delete(task);
  }

  @Override
  public List<Task> all() throws IOException {
    String url = getDefaultUrl();

    return restClient.doGet(url, GsonTypeTokens.LIST_TASK);
  }

  @Override
  public boolean contains(Task task) throws IOException {
    return all().contains(task);
  }

  @Override
  public TaskUpdateFlow update(Task task) {
    return new TaskUpdateFlowImpl(tasks, columns, task);
  }

  private String getDefaultUrl() {
    return conf.getApiUrl(currentWorkspace, currentProject.getId()) + "icebox/tasks.json";
  }

  public IceBox using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}

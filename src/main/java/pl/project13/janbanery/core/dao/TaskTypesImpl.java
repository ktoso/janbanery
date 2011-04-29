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
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class TaskTypesImpl implements TaskTypes {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;

  private Workspace currentWorkspace;
  private Project   currentProject;

  private RestClient restClient;

  public TaskTypesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public TaskType create(TaskType taskType) throws IOException {
    throw new NotYetImplementedException();  // todo implement me
  }

  @Override
  public TaskType update(TaskType taskType, TaskType newValues) throws IOException {
    throw new NotYetImplementedException();  // todo implement me
  }

  @Override
  public List<TaskType> all() throws IOException {
    String url = getDefaultGetUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_TASK_TYPE);
  }

  @Override
  public TaskType any() throws IOException {
    List<TaskType> taskTypes = all();

    if (taskTypes.size() == 0) {
      throw new EntityNotFoundException("Could not find any task types.");
    }

    return taskTypes.get(0);
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

  private String getDefaultGetUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "task_types.json";
  }
}

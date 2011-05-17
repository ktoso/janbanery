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
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class UsersImpl implements Users {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  public UsersImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public User current() {
    String url = getMyUserUrl();

    return restClient.doGet(url, GsonTypeTokens.USER);
  }

  @Override
  public List<User> all() {
    String url = getProjectUsersUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_USER);
  }

  @Override
  public List<User> inProject(Project project) throws ServerCommunicationException {
    String url = getProjectUsersUrl(project);
    return restClient.doGet(url, GsonTypeTokens.LIST_USER);
  }

  private String getMyUserUrl() {
    return conf.getApiUrl() + "user.json";
  }

  private String getProjectUsersUrl() {
    return getProjectUsersUrl(currentProject);
  }

  private String getProjectUsersUrl(Project project) {
    return conf.getApiUrl(currentWorkspace, project.getId(), "users");
  }

  public Users using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}


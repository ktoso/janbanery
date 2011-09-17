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

package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.auth.AuthProvider;
import pl.project13.janbanery.core.dao.*;
import pl.project13.janbanery.core.rest.AsyncHttpClientRestClient;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.encoders.ReflectionBodyGenerator;
import pl.project13.janbanery.exceptions.ProjectNotFoundException;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.util.concurrent.ExecutionException;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Janbanery {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  public Janbanery(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  private RestClient getRestClient(Configuration conf, AsyncHttpClient asyncHttpClient, Gson gson, ReflectionBodyGenerator bodyGenerator) {
    return new AsyncHttpClientRestClient(conf, gson, asyncHttpClient, bodyGenerator);
  }

  public AuthProvider getAuthMode() {
    return conf.getAuthProvider();
  }

  /**
   * Delegates to {@link Workspaces#byName(String)} and keeps this workspace as the default one.
   * This method does also setup a default project if there is one. If you want to select the {@link Project}
   * you'll be working on, please get the return value of this method, and then call {@link Janbanery#using(Project)}.
   * From now on, all calls requiring a workspace will use this workspace and it's first present project,
   * you may change the default workspace at anytime by calling this method again.
   *
   * @param name the name of the workspace to be used
   * @return the fetched workspace
   * @throws ServerCommunicationException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public Janbanery usingWorkspace(String name) throws ServerCommunicationException, ExecutionException, InterruptedException {
    Workspace workspace = workspaces().byName(name);

    Project firstProject = workspace.getProjects().get(0);

    return using(workspace).using(firstProject);
  }

  /**
   * Setup the used {@link Project} by it's name.
   *
   * @param name the name of the project you want to use (it should be from the current {@link Workspace}
   * @return the now being used project
   * @throws ProjectNotFoundException     will be thrown if no project with such name isSubscribedTo in the current workspace
   * @throws ServerCommunicationException if unable to communicate with the server
   */
  public Janbanery usingProject(String name) throws ServerCommunicationException {
    return usingProject(currentWorkspace, name);
  }

  public Janbanery usingProject(Workspace workspace, String name) throws ServerCommunicationException {
    currentWorkspace = workspace;

    for (Project project : currentWorkspace.getProjects()) {
      if (project.getName().equals(name)) {
        return using(workspace).using(project);
      }
    }
    throw new ProjectNotFoundException("The workspace '" + workspace.getName() + "' has no project named '" + name + "'.");
  }

  private Janbanery using(Workspace workspace) {
    currentWorkspace = workspace;

    return this;
  }

  private Janbanery using(Project project) {
    currentProject = project;

    return this;
  }

  /* return initially setup instances of dao objects */

  public Tasks tasks() {
    return new TasksImpl(columns(), conf, restClient).using(currentWorkspace, currentProject);
  }

  public IssuesOf issues() {
    return new IssuesImpl(conf, restClient).using(currentWorkspace);
  }

  public SubTasks subTasks() {
    return new SubTasksImpl(conf, restClient).using(currentWorkspace);
  }

  public CommentsOf comments() {
    return new CommentsImpl(conf, restClient).using(currentWorkspace);
  }

  public IceBox iceBox() {
    return new IceBoxImpl(tasks(), columns(), conf, restClient).using(currentWorkspace, currentProject);
  }

  public Archive archive() {
    return new ArchiveImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public TaskTypes taskTypes() {
    return new TaskTypesImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Users users() {
    return new UsersImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Subscriptions subscriptions() {
    return new SubscriptionsImpl(conf, restClient).using(currentWorkspace);
  }

  public Workspaces workspaces() {
    return new WorkspacesImpl(conf, restClient).using(currentWorkspace);
  }

  public Columns columns() {
    return new ColumnsImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Estimates estimates() {
    return new EstimatesImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public MembershipsOf memberships() {

    return new MembershipsImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Projects projects() {
    return new ProjectsImpl(workspaces()).using(currentWorkspace, currentProject);
  }

  public Log log() {
    return new LogImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Search search() {
    return new SearchImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  /**
   * Set a new configuration to be used.
   * This is useful when for example, you want to log into a different account,
   * while holding the same Janbanery instance.
   *
   * @param conf thew new Configuration to be used
   */
  public void setConf(Configuration conf) {
    this.conf = conf;
  }

  /**
   * If is very important that you call this method after you're finished working with kanbanery.
   * It will close all underlying threads and free a lot of memory used by the RestClient.
   */
  public void close() {
    restClient.close();
  }
}

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
import pl.project13.janbanery.encoders.FormUrlEncodedBodyGenerator;
import pl.project13.janbanery.encoders.ReflectionsBodyGenerator;
import pl.project13.janbanery.exceptions.ProjectNotFoundException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Janbanery {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration   conf;
  private AsyncHttpClient asyncHttpClient;
  private Gson            gson;

  private Workspace currentWorkspace;
  private Project   currentProject;

  private FormUrlEncodedBodyGenerator bodyGenerator;

  public Janbanery(Configuration conf, AsyncHttpClient asyncHttpClient, Gson gson, ReflectionsBodyGenerator bodyGenerator) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.gson = gson;
    this.bodyGenerator = bodyGenerator;
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
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public Workspace usingWorkspace(String name) throws IOException, ExecutionException, InterruptedException {
    Workspace workspace = workspaces().byName(name);

    Project firstProject = workspace.getProjects().get(0);
    using(firstProject);

    return using(workspace);
  }

  /**
   * Setup the used {@link Project} by it's name.
   *
   * @param name the name of the project you want to use (it should be from the current {@link Workspace}
   * @return the now being used project
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws ProjectNotFoundException will be thrown if no project with such name isSubscribedTo in the current workspace
   */
  public Project usingProject(String name) throws IOException, ExecutionException, InterruptedException, ProjectNotFoundException {
    return this.usingProject(currentWorkspace, name);
  }

  public Project usingProject(Workspace workspace, String name) throws IOException, ExecutionException, InterruptedException, ProjectNotFoundException {
    for (Project project : workspace.getProjects()) {
      if (project.getName().equals(name)) {
        return using(project);
      }
    }
    throw new ProjectNotFoundException("The workspace '" + workspace.getName() + "' has no project named '" + name + "'.");
  }

  private Workspace using(Workspace workspace) {
    currentWorkspace = workspace;
    return currentWorkspace;
  }

  private Project using(Project project) {
    currentProject = project;
    return currentProject;
  }

  /* return initially setup instances of dao objects */

  public Tasks tasks() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new TasksImpl(columns(), conf, restClient, gson).using(currentWorkspace, currentProject);
  }

  public SubTasks subTasks() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new SubTasksImpl(conf, restClient).using(currentWorkspace);
  }

  public IceBox iceBox() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new IceBoxImpl(tasks(), columns(), conf, restClient).using(currentWorkspace, currentProject);
  }

  public Archive archive() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator); // todo improve this to be mockable
    return new ArchiveImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public TaskTypes taskTypes() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new TaskTypesImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Users users() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    UsersImpl users = new UsersImpl(conf, restClient);
    return users.using(currentWorkspace, currentProject);
  }

  public Subscriptions subscriptions() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new SubscriptionsImpl(conf, restClient).using(currentWorkspace);
  }

  public Workspaces workspaces() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new WorkspacesImpl(conf, restClient);
  }

  public Columns columns() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new ColumnsImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Estimates estimates() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);// todo improve this to be mockable
    return new EstimatesImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Projects projects() {
    return new ProjectsImpl(workspaces());
  }
}

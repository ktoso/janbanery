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

import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class ProjectsImpl implements Projects {

  private Workspaces workspaces;

  private Workspace currentWorkspace;
  private Project currentProject;

  public ProjectsImpl(Workspaces workspaces) {
    this.workspaces = workspaces;
  }

  @Override
  public List<Project> all() {
    Workspace workspace = workspaces.byName(currentWorkspace.getName());

    return workspace.getProjects();
  }

  @Override
  public List<Project> allAcrossWorkspaces() {
    List<Workspace> allWorkspaces = workspaces.all();

    List<Project> allProjects = newArrayList();

    for (Workspace workspace : allWorkspaces) {
      allProjects.addAll(workspace.getProjects());
    }

    return allProjects;
  }

  @Override
  public Project byId(Long id) throws EntityNotFoundException {
    List<Workspace> allWorkspaces = workspaces.all();

    for (Workspace workspace : allWorkspaces) {
      List<Project> allItsProjects = workspace.getProjects();
      for (Project project : allItsProjects) {
        if (project.getId().equals(id)) {
          return project;
        }
      }
    }

    throw new EntityNotFoundException("Could not find project with id: " + id);
  }

  @Override
  public Project current() {
    return currentProject;
  }

  public Projects using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}


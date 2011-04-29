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

package pl.project13.janbanery.resources;

import pl.project13.janbanery.resources.additions.ReadOnly;

import java.io.Serializable;
import java.util.List;

/**
 * A Workspace object is the root of all other objects in the project flow in Kanbanery.
 * A workspace has projects which have tasks etc.
 * <p/>
 * A default workspace should be chosen while working with Janbanery to
 * increase smoothness of all API calls inside your code :-)
 *
 * @author Konrad Malawski
 */
@ReadOnly
public class Workspace extends KanbaneryResource implements Serializable {

  /**
   * Workspace id
   */
  @ReadOnly
  private Integer id;

  /**
   * Workspace name
   */
  @ReadOnly
  private String name;

  /**
   * List of {@link Project} resources accessible by the current {@link User}
   */
  @ReadOnly
  private List<Project> projects;

  public Workspace() {
  }

  @Override
  public String getResourceId() {
    return "workspace";
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Workspace workspace = (Workspace) o;

    if (createdAt != null ? !createdAt.equals(workspace.createdAt) : workspace.createdAt != null) {
      return false;
    }
    if (id != null ? !id.equals(workspace.id) : workspace.id != null) {
      return false;
    }
    if (name != null ? !name.equals(workspace.name) : workspace.name != null) {
      return false;
    }
    if (projects != null ? !projects.equals(workspace.projects) : workspace.projects != null) {
      return false;
    }
    if (type != null ? !type.equals(workspace.type) : workspace.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(workspace.updatedAt) : workspace.updatedAt != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (projects != null ? projects.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

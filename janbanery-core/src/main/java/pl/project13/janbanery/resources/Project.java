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

/**
 * A project is the Home for all Tasks etc.
 *
 * @author Konrad Malawski
 */
@ReadOnly
public class Project extends KanbaneryResource implements Serializable {

  /**
   * Project id
   */
  @ReadOnly
  private Long id;

  /**
   * Project name
   */
  @ReadOnly
  private String name;

  public Project() {
  }

  public Project(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getResourceId() {
    return "projects";
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Project project = (Project) o;

    if (createdAt != null ? !createdAt.equals(project.createdAt) : project.createdAt != null) {
      return false;
    }
    if (id != null ? !id.equals(project.id) : project.id != null) {
      return false;
    }
    if (name != null ? !name.equals(project.name) : project.name != null) {
      return false;
    }
    if (type != null ? !type.equals(project.type) : project.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(project.updatedAt) : project.updatedAt != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
//    result = 31 * result + (url != null ? url.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Project");
    sb.append("{name='").append(name).append('\'');
    sb.append(", id=").append(id);
    sb.append('}');
    return sb.toString();
  }
}

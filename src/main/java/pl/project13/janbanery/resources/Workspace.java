package pl.project13.janbanery.resources;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Workspace extends KanbaneryResource implements Serializable {
  Integer             id; // Workspace id
  String              name; // 	Workspace name
  Collection<Project> projects; // Array of Project resources accessible by user

  DateTime created_at; // Creation time
  DateTime updated_at; // Last update time
  String   type; // Type of this resource, set to "Workspace". Only for JSON responses. In XML responses node name is "workspace".

  public Workspace() {
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

  public Collection<Project> getProjects() {
    return projects;
  }

  public void setProjects(Collection<Project> projects) {
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

    if (created_at != null ? !created_at.equals(workspace.created_at) : workspace.created_at != null) {
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
    if (updated_at != null ? !updated_at.equals(workspace.updated_at) : workspace.updated_at != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (projects != null ? projects.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

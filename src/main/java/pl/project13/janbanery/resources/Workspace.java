package pl.project13.janbanery.resources;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Workspace implements Serializable {
  Integer             id; // Workspace id
  String              name; // 	Workspace name
  Collection<Project> projects; // Array of Project resources accessible by user

  Date                created_at; // Creation time
  Date                updated_at; // Last update time
  String              type; // Type of this resource, set to "Workspace". Only for JSON responses. In XML responses node name is "workspace".

  public Workspace() {
  }

  public Workspace(Integer id, String name, Collection<Project> projects, Date created_at, Date updated_at, String type) {
    this.id = id;
    this.name = name;
    this.projects = projects;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.type = type;
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

  public Date getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public Date getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(Date updated_at) {
    this.updated_at = updated_at;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Workspace workspace = (Workspace) o;

    if (created_at != null ? !created_at.equals(workspace.created_at) : workspace.created_at != null) return false;
    if (id != null ? !id.equals(workspace.id) : workspace.id != null) return false;
    if (name != null ? !name.equals(workspace.name) : workspace.name != null) return false;
    if (projects != null ? !projects.equals(workspace.projects) : workspace.projects != null) return false;
    if (type != null ? !type.equals(workspace.type) : workspace.type != null) return false;
    if (updated_at != null ? !updated_at.equals(workspace.updated_at) : workspace.updated_at != null) return false;

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

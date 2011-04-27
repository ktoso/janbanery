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

//  /**
//   * Full URL to the project
//   */
//  private String  url;

  public Project() {
  }

  @Override public String getResourceId() {
    return "projects";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public String getUrl() {
//    return url;
//  }
//
//  public void setUrl(String url) {
//    this.url = url;
//  }

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
//    if (url != null ? !url.equals(project.url) : project.url != null) {
//      return false;
//    }

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
}

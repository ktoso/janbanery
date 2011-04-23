package pl.project13.janbanery.resources;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Project extends KanbaneryResource implements Serializable {
  private Integer id;//Project id
  private String  name;//Project name
  private String  url;//Full URL to the project

  public Project() {
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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
    if (url != null ? !url.equals(project.url) : project.url != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (url != null ? url.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

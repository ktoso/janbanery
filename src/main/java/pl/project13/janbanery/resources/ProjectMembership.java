package pl.project13.janbanery.resources;

import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class ProjectMembership extends KanbaneryResource implements Serializable {

  @Required
  @Settable(On.Create)
  private String email;

  @Required
  @Settable(On.CreateOrUpdate)
  private Permission permission;

  private Integer projectId;

  private Integer userId;

  public ProjectMembership() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProjectMembership that = (ProjectMembership) o;

    if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (permission != null ? !permission.equals(that.permission) : that.permission != null) {
      return false;
    }
    if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
      return false;
    }
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = email != null ? email.hashCode() : 0;
    result = 31 * result + (permission != null ? permission.hashCode() : 0);
    result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

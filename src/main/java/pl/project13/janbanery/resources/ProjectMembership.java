package pl.project13.janbanery.resources;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class ProjectMembership extends KanbaneryResource implements Serializable {

  String  email; // yes	on create 	User email
  String  permission; // yes	on create and update	One of permission types: [ manager, member, viewer ]
  Integer project_id; // no	 	Project id
  Integer user_id; // no	 	User id

  public ProjectMembership() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public Integer getProject_id() {
    return project_id;
  }

  public void setProject_id(Integer project_id) {
    this.project_id = project_id;
  }

  public Integer getUser_id() {
    return user_id;
  }

  public void setUser_id(Integer user_id) {
    this.user_id = user_id;
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

    if (created_at != null ? !created_at.equals(that.created_at) : that.created_at != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (permission != null ? !permission.equals(that.permission) : that.permission != null) {
      return false;
    }
    if (project_id != null ? !project_id.equals(that.project_id) : that.project_id != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (updated_at != null ? !updated_at.equals(that.updated_at) : that.updated_at != null) {
      return false;
    }
    if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = email != null ? email.hashCode() : 0;
    result = 31 * result + (permission != null ? permission.hashCode() : 0);
    result = 31 * result + (project_id != null ? project_id.hashCode() : 0);
    result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

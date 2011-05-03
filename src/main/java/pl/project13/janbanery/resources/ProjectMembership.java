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

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * Describes if an user is part of a project and if yes, what role he has in it.
 * Please note that there are only 3 levels of Membership currently in Kanbanery, as defined in {@link Permission}.
 *
 * @author Konrad Malawski
 */
public class ProjectMembership extends KanbaneryResource implements Serializable {

  /**
   * The resources ID
   */
  @ReadOnly
  private Long id;

  /**
   * User email (weird?)
   */
  @Required
  @Settable(On.Create)
  private String email;

  /**
   * What kind of permissions he has
   */
  @Required
  @Settable(On.CreateOrUpdate)
  private Permission permission;

  /**
   * Id of the project we're talking about here
   */
  @SerializedName("project_id")
  private Integer projectId;

  /**
   * The id of the user we're checking here
   */
  @SerializedName("user_id")
  private Integer userId;

  public ProjectMembership() {
  }

  public ProjectMembership(String email, Permission permission) {
    this.email = email;
    this.permission = permission;
  }

  @Override
  public String getResourceId() {
    return "project_membership";
  }

  public Long getId() {
    return id;
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


  public static class Builder {

    private ProjectMembership instance;

    public Builder() {
      instance = new ProjectMembership();
    }

    public Builder email(String email) {
      instance.email = email;
      return this;
    }

    public Builder permission(Permission permission) {
      instance.permission = permission;
      return this;
    }

    public ProjectMembership build() {
      Preconditions.checkNotNull(instance.email);
      Preconditions.checkNotNull(instance.permission);
      return instance;
    }
  }
}

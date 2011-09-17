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

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import pl.project13.janbanery.resources.additions.ReadOnly;

import java.io.Serializable;

/**
 * A user object represents an account able to login into Kanbanery, it can have tasks assigned and do things.
 * Please note that there are Permissions for API access that a user, and thus Janbanery (as it's using a User account internally)
 * can have, these are:
 * <p/>
 * <ul>
 * <li>project viewer (read only user)</li>
 * <li>project member</li>
 * <li>project manager</li>
 * </ul>
 * You can change a user's role in a specific project on the "Project members" page.
 *
 * @author Konrad Malawski
 */
@ReadOnly
public class User extends KanbaneryResource implements Serializable {

  /**
   * The user's id
   */
  @ReadOnly
  protected Long id;

  /**
   * The user's API token
   */
  @SerializedName("api_token")
  @ReadOnly
  protected String apiToken;

  /**
   * The user's email
   */
  @ReadOnly
  protected String email;

  /**
   * The user's first name
   */
  @SerializedName("first_name")
  protected String firstName;

  /**
   * The user's last name
   */
  @SerializedName("last_name")
  protected String lastName;

  /**
   * User's avatar URL (at gravatar.com), determined from his email address
   */
  @SerializedName("gravatar_url")
  @ReadOnly
  protected String gravatarUrl;

  public User() {
  }

  @Override
  public String getResourceId() {
    return "user";
  }

  public Long getId() {
    return id;
  }

  public String getApiToken() {
    return apiToken;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGravatarUrl() {
    return gravatarUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    User user = (User) o;

    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
      return false;
    }
    if (gravatarUrl != null ? !gravatarUrl.equals(user.gravatarUrl) : user.gravatarUrl != null) {
      return false;
    }
    if (id != null ? !id.equals(user.id) : user.id != null) {
      return false;
    }
    if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (gravatarUrl != null ? gravatarUrl.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("User");
    sb.append("{id=").append(id);
    sb.append(", email='").append(email).append('\'');
    sb.append(", firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public static class NoOne extends User {
    public NoOne() {
      super.id = null;
      super.email = "";
      super.firstName = "Nobody";
      super.lastName = "";
      super.apiToken = "IAmNotARealAccount";
      super.createdAt = new DateTime();
      super.gravatarUrl = "https://janbanery.kanbanery.com/images/no-user.png";
      super.type = "User";
    }


  }
}

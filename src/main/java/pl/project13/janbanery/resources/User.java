package pl.project13.janbanery.resources;

import java.io.Serializable;

/**
 * A user object represents an account able to login into Kanbanery, it can have tasks assigned and do things.
 * Please note that there are Permissions for API access that a user, and thus Janbanery (as it's using a User account internally)
 * can have, these are:
 * <p/>
 * <ul>
 * <li> project viewer (read only user)</li>
 * <li>project member</li>
 * <li>project manager</li>
 * </ul>
 * You can change a user's role in a specific project on the "Project members" page.
 *
 * @author Konrad Malawski
 */
public class User extends KanbaneryResource implements Serializable {
  String apiToken; // User Token
  User   email; // String	email
  String firstName; // 	User first name
  String lastName; // User last name
  String gravatarUrl; // User's avatar (at gravatar.com)

  public User() {
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public User getEmail() {
    return email;
  }

  public void setEmail(User email) {
    this.email = email;
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

  public void setGravatarUrl(String gravatarUrl) {
    this.gravatarUrl = gravatarUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (apiToken != null ? !apiToken.equals(user.apiToken) : user.apiToken != null) {
      return false;
    }
    if (created_at != null ? !created_at.equals(user.created_at) : user.created_at != null) {
      return false;
    }
    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
      return false;
    }
    if (gravatarUrl != null ? !gravatarUrl.equals(user.gravatarUrl) : user.gravatarUrl != null) {
      return false;
    }
    if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
      return false;
    }
    if (type != null ? !type.equals(user.type) : user.type != null) {
      return false;
    }
    if (updated_at != null ? !updated_at.equals(user.updated_at) : user.updated_at != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = apiToken != null ? apiToken.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (gravatarUrl != null ? gravatarUrl.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

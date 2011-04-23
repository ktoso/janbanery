package pl.project13.janbanery.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class User extends KanbaneryResource implements Serializable {
  String api_token; // User Token
  User   email; // String	email
  String first_name; // 	User first name
  String last_name; // User last name
  String gravatar_url; // User's avatar (at gravatar.com)

  public User() {
  }

  public String getApiToken() {
    return api_token;
  }

  public void setApi_token(String api_token) {
    this.api_token = api_token;
  }

  public User getEmail() {
    return email;
  }

  public void setEmail(User email) {
    this.email = email;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getGravatar_url() {
    return gravatar_url;
  }

  public void setGravatar_url(String gravatar_url) {
    this.gravatar_url = gravatar_url;
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

    if (api_token != null ? !api_token.equals(user.api_token) : user.api_token != null) {
      return false;
    }
    if (created_at != null ? !created_at.equals(user.created_at) : user.created_at != null) {
      return false;
    }
    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    if (first_name != null ? !first_name.equals(user.first_name) : user.first_name != null) {
      return false;
    }
    if (gravatar_url != null ? !gravatar_url.equals(user.gravatar_url) : user.gravatar_url != null) {
      return false;
    }
    if (last_name != null ? !last_name.equals(user.last_name) : user.last_name != null) {
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
    int result = api_token != null ? api_token.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
    result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
    result = 31 * result + (gravatar_url != null ? gravatar_url.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

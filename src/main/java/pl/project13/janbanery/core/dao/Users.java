package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.User;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Users {
  /**
   * Fetches the currently being used user.
   * The "me" user, one might say.
   *
   * @return the currently being used user for all API calls
   * @throws IOException if the response body could not be fetched
   */
  User current() throws IOException;

  List<User> all() throws IOException;

  List<User> allInProject(Project project) throws IOException;

  User nobody() throws IOException;
}

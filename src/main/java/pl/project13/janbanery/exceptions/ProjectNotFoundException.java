package pl.project13.janbanery.exceptions;

import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

/**
 * Thrown if you try to <strong>access</strong> or <strong>use</strong> a {@link Project}
 * that does not exist in that particular {@link Workspace}
 *
 * @author Konrad Malawski
 */
public class ProjectNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 949827205009771235L;

  public ProjectNotFoundException() {
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }

  public ProjectNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProjectNotFoundException(Throwable cause) {
    super(cause);
  }
}

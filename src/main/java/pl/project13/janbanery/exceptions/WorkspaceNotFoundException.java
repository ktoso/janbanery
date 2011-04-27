package pl.project13.janbanery.exceptions;

/**
 * @author Konrad Malawski
 */
public class WorkspaceNotFoundException extends EntityNotFoundException {
  private static final long serialVersionUID = 5852726515015604743L;

  public WorkspaceNotFoundException() {
    super();
  }

  public WorkspaceNotFoundException(String message) {
    super(message);
  }

  public WorkspaceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public WorkspaceNotFoundException(Throwable cause) {
    super(cause);
  }

}

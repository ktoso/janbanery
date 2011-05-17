package pl.project13.janbanery.exceptions;

/**
 * Thrown for example when the underlying API used by Janbanery encounters an IOException
 * or something else went wrong during communicating with the Kanbanery service.
 *
 * @author Konrad Malawski
 */
public class ServerCommunicationException extends RuntimeException {

  private static final long serialVersionUID = 6980476730829703852L;

  public ServerCommunicationException() {
  }

  public ServerCommunicationException(String detailMessage) {
    super(detailMessage);
  }

  public ServerCommunicationException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public ServerCommunicationException(Throwable throwable) {
    super(throwable);
  }

}

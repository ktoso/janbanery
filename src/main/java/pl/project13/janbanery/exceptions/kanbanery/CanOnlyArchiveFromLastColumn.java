package pl.project13.janbanery.exceptions.kanbanery;

/**
 * @author Konrad Malawski
 */
public class CanOnlyArchiveFromLastColumn extends InvalidEntityKanbaneryException {
  private static final long serialVersionUID = 6904587027128051151L;

  public CanOnlyArchiveFromLastColumn(String message) {
    super(message);
  }

  public CanOnlyArchiveFromLastColumn(String message, Throwable cause) {
    super(message, cause);
  }

  public CanOnlyArchiveFromLastColumn(Throwable cause) {
    super(cause);
  }


  public static boolean isBestExceptionFor(String response) {
    return response.contains("");//todo
  }
}

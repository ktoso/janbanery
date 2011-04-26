package pl.project13.janbanery.resources.additions;

/**
 * @author Konrad Malawski
 */
public enum TaskLocation {
  NEXT("next_column"),
  PREVIOUS("prev_column"),

  BOARD("board"),
  ICEBOX("icebox"),
  ARCHIVE("archive");

  private String requestBody;

  TaskLocation(String requestBody) {
    this.requestBody = requestBody;
  }

  public String requestBody() {
    return String.format("task[location]=%s", requestBody);
  }
}

package pl.project13.janbanery.resources.additions;

import pl.project13.janbanery.resources.Task;

/**
 * A helper class to specify a {@link Task}s location in Kanbanery.
 * NEXT and PREVIOUS are only used when performing updates.
 *
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

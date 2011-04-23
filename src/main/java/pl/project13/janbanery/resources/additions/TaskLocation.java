package pl.project13.janbanery.resources.additions;

/**
 * @author Konrad Malawski
 */
public enum TaskLocation {
  NEXT("tasks[next]"),
  PREVIOUS("tasks[previous]"),
  ARCHIVE("tasks[archive]"),
  ICEBOX("tasks[icebox]");

  private String jsonName;

  TaskLocation(String jsonName) {
    this.jsonName = jsonName;
  }

  public String jsonName() {
    return jsonName;
  }
}

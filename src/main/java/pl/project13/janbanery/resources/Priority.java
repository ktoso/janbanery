package pl.project13.janbanery.resources;

import pl.project13.janbanery.exceptions.NoSuchPriorityException;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public enum Priority {
  LOW(0),
  MEDIUM(1),
  HIGH(2);

  private Integer priorityId;

  Priority(Integer priorityId) {
    this.priorityId = priorityId;
  }

  public Integer id() {
    return priorityId;
  }

  public static Priority fromInteger(Integer id) {
    for (Priority priority : Priority.values()) {
      if (priority.priorityId.equals(id)) {
        return priority;
      }
    }
    throw new NoSuchPriorityException("Tried to create priority for priority id = " + id);
  }

}

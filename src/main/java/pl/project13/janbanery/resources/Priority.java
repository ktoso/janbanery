package pl.project13.janbanery.resources;

import pl.project13.janbanery.exceptions.NoSuchPriorityException;

/**
 * Task priorities. Currently kanbanery is only supporting 3 levels of priorities - {0, 1, 2}
 * with 0 being the LOW and 2 being the HIGH priority.
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

  public static Priority fromPriorityId(Integer id) {
    for (Priority priority : Priority.values()) {
      if (priority.priorityId.equals(id)) {
        return priority;
      }
    }
    throw new NoSuchPriorityException("Tried to create priority for priority id = " + id);
  }

}

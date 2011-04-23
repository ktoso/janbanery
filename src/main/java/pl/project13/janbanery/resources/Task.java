package pl.project13.janbanery.resources;

import org.joda.time.DateTime;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Task extends KanbaneryResource implements Serializable {

  @Required
  private String title; // Title

  @Required(alternativeTo = "taskTypeName")
  private Integer taskTypeId; // Task type, ie. "Bug", "Story". Instead of setting this id you can just settask_type_name to "Bug"

  @Required(alternativeTo = "taskTypeId")
  private String taskTypeName;

  @Required
  @Settable(On.Update)
  private Integer columnId;

  private Integer creatorId;

  @Settable(On.CreateOrUpdate)
  private String description;

  @Settable(On.CreateOrUpdate)
  private Integer estimateId; // Estimate

  private Integer ownerId; // Who is currently assigned to it

  @Settable(On.CreateOrUpdate)
  private Integer position; // Position in column, 1-based

  @Settable(On.CreateOrUpdate)
  private Priority priority; // Priority (0, 1 or 2)

  @Settable(On.CreateOrUpdate)
  private Boolean readyToPull; // If task is ready to be pulled

  private Boolean blocked; // If task is blocked by other task(s)

  private DateTime movedAt; // On task was moved to currentUser column

  public Task() {
  }

  public Task(String title, String taskTypeName) {
    this.title = title;
    this.taskTypeName = taskTypeName;
  }

  public Task(String title, Integer taskTypeId) {
    this.title = title;
    this.taskTypeId = taskTypeId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getTaskTypeId() {
    return taskTypeId;
  }

  public void setTaskTypeId(Integer taskTypeId) {
    this.taskTypeId = taskTypeId;
  }

  public String getTaskTypeName() {
    return taskTypeName;
  }

  public void setTaskTypeName(String taskTypeName) {
    this.taskTypeName = taskTypeName;
  }

  public Integer getColumnId() {
    return columnId;
  }

  public void setColumnId(Integer columnId) {
    this.columnId = columnId;
  }

  public Integer getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getEstimateId() {
    return estimateId;
  }

  public void setEstimateId(Integer estimateId) {
    this.estimateId = estimateId;
  }

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Boolean getReadyToPull() {
    return readyToPull;
  }

  public void setReadyToPull(Boolean readyToPull) {
    this.readyToPull = readyToPull;
  }

  public Boolean getBlocked() {
    return blocked;
  }

  public void setBlocked(Boolean blocked) {
    this.blocked = blocked;
  }

  public DateTime getMovedAt() {
    return movedAt;
  }

  public void setMovedAt(DateTime movedAt) {
    this.movedAt = movedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    Task task = (Task) o;

    if (blocked != null ? !blocked.equals(task.blocked) : task.blocked != null) {
      return false;
    }
    if (columnId != null ? !columnId.equals(task.columnId) : task.columnId != null) {
      return false;
    }
    if (creatorId != null ? !creatorId.equals(task.creatorId) : task.creatorId != null) {
      return false;
    }
    if (description != null ? !description.equals(task.description) : task.description != null) {
      return false;
    }
    if (estimateId != null ? !estimateId.equals(task.estimateId) : task.estimateId != null) {
      return false;
    }
    if (movedAt != null ? !movedAt.equals(task.movedAt) : task.movedAt != null) {
      return false;
    }
    if (ownerId != null ? !ownerId.equals(task.ownerId) : task.ownerId != null) {
      return false;
    }
    if (position != null ? !position.equals(task.position) : task.position != null) {
      return false;
    }
    if (priority != task.priority) {
      return false;
    }
    if (readyToPull != null ? !readyToPull.equals(task.readyToPull) : task.readyToPull != null) {
      return false;
    }
    if (taskTypeId != null ? !taskTypeId.equals(task.taskTypeId) : task.taskTypeId != null) {
      return false;
    }
    if (taskTypeName != null ? !taskTypeName.equals(task.taskTypeName) : task.taskTypeName != null) {
      return false;
    }
    if (title != null ? !title.equals(task.title) : task.title != null) {
      return false;
    }

    return true;
  }

  // todo generate this using intellij plugin ;-)
  public static class Builder {
    private Task task;

    public Builder(String title, Integer taskTypeId) {
      this.task = new Task(title, taskTypeId);
    }

    public Builder(String title, String taskTypeName) {
      this.task = new Task(title, taskTypeName);
    }

    public Builder priority(Priority priority) {
      task.priority = priority;
      return this;
    }

    public Builder description(String description) {
      task.description = description;
      return this;
    }

    public Builder estimateId(Integer estimateId) {
      task.estimateId = estimateId;
      return this;
    }

    /**
     * The position in the column, 1 based.
     *
     * @param position the position to be set
     * @return the same builder instance
     */
    public Builder position(Integer position) {
      task.position = position;
      return this;
    }

    public Task build() {
      return task;
    }

  }
}
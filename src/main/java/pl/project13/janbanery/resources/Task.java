package pl.project13.janbanery.resources;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Task extends KanbaneryResource implements Serializable {
  String   title; // yes	on create and update	Title
  Integer  taskTypeId; // yes	on create and update	Task type, ie. "Bug", "Story". Instead of setting this id you can just settask_type_name to "Bug"
  Integer  columnId; // no	on update	Column
  Integer  creatorId; // no	 	Who created it
  String   description; // no	on create and update	Description
  Integer  estimateId; // no	on create and update	Estimate
  Integer  ownerId; // no	 	Who is currently assigned to it
  Integer  position; // no	on create and update	Position in column, 1-based
  Priority priority; // no	on create and update	Priority (0, 1 or 2)
  Boolean  readyToPull; // 	no	on create and update	If task is ready to be pulled
  Boolean  blocked; // no	 	If task is blocked by other task(s)
  DateTime movedAt; // no	 	When task was moved to currentUser column

  public Task() {
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

    if (priority != null ? !priority.equals(task.priority) : task.priority != null) {
      return false;
    }
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
    if (readyToPull != null ? !readyToPull.equals(task.readyToPull) : task.readyToPull != null) {
      return false;
    }
    if (taskTypeId != null ? !taskTypeId.equals(task.taskTypeId) : task.taskTypeId != null) {
      return false;
    }
    if (title != null ? !title.equals(task.title) : task.title != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (taskTypeId != null ? taskTypeId.hashCode() : 0);
    result = 31 * result + (columnId != null ? columnId.hashCode() : 0);
    result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (estimateId != null ? estimateId.hashCode() : 0);
    result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    result = 31 * result + (priority != null ? priority.hashCode() : 0);
    result = 31 * result + (readyToPull != null ? readyToPull.hashCode() : 0);
    result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
    result = 31 * result + (movedAt != null ? movedAt.hashCode() : 0);
    return result;
  }
}

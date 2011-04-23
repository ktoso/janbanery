package pl.project13.janbanery.resources;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class SubTask extends KanbaneryResource implements Serializable {
  String  body; // yes	on create and update	Short description
  Integer taskId; // 	no	 	Task
  Integer creatorId; // no	 	Who created it
  Boolean completed; // no	on create and update	If it was completed

  public SubTask() {
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  public Integer getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Integer creatorId) {
    this.creatorId = creatorId;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SubTask subTask = (SubTask) o;

    if (body != null ? !body.equals(subTask.body) : subTask.body != null) {
      return false;
    }
    if (completed != null ? !completed.equals(subTask.completed) : subTask.completed != null) {
      return false;
    }
    if (created_at != null ? !created_at.equals(subTask.created_at) : subTask.created_at != null) {
      return false;
    }
    if (creatorId != null ? !creatorId.equals(subTask.creatorId) : subTask.creatorId != null) {
      return false;
    }
    if (taskId != null ? !taskId.equals(subTask.taskId) : subTask.taskId != null) {
      return false;
    }
    if (type != null ? !type.equals(subTask.type) : subTask.type != null) {
      return false;
    }
    if (updated_at != null ? !updated_at.equals(subTask.updated_at) : subTask.updated_at != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = body != null ? body.hashCode() : 0;
    result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
    result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
    result = 31 * result + (completed != null ? completed.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

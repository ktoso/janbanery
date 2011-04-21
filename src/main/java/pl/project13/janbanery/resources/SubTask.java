package pl.project13.janbanery.resources;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class SubTask extends KanbaneryResource implements Serializable {
  String  body; // yes	on create and update	Short description
  Integer task_id; // 	no	 	Task
  Integer creator_id; // no	 	Who created it
  Boolean completed; // no	on create and update	If it was completed

  public SubTask() {
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Integer getTask_id() {
    return task_id;
  }

  public void setTask_id(Integer task_id) {
    this.task_id = task_id;
  }

  public Integer getCreator_id() {
    return creator_id;
  }

  public void setCreator_id(Integer creator_id) {
    this.creator_id = creator_id;
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
    if (creator_id != null ? !creator_id.equals(subTask.creator_id) : subTask.creator_id != null) {
      return false;
    }
    if (task_id != null ? !task_id.equals(subTask.task_id) : subTask.task_id != null) {
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
    result = 31 * result + (task_id != null ? task_id.hashCode() : 0);
    result = 31 * result + (creator_id != null ? creator_id.hashCode() : 0);
    result = 31 * result + (completed != null ? completed.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

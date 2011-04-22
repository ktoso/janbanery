package pl.project13.janbanery.resources;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Task extends KanbaneryResource implements Serializable {
  String   title; // yes	on create and update	Title
  Integer  task_type_id; // yes	on create and update	Task type, ie. "Bug", "Story". Instead of setting this id you can just settask_type_name to "Bug"
  Integer  column_id; // no	on update	Column
  Integer  creator_id; // no	 	Who created it
  String   description; // no	on create and update	Description
  Integer  estimate_id; // no	on create and update	Estimate
  Integer  owner_id; // no	 	Who is currently assigned to it
  Integer  position; // no	on create and update	Position in column, 1-based
  Priority Integer; // no	on create and update	Priority (0, 1 or 2)
  Boolean  ready_to_pull; // 	no	on create and update	If task is ready to be pulled
  Boolean  blocked; // no	 	If task is blocked by other task(s)
  DateTime moved_at; // no	 	When task was moved to current column

  public Task() {
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

    if (Integer != null ? !Integer.equals(task.Integer) : task.Integer != null) {
      return false;
    }
    if (blocked != null ? !blocked.equals(task.blocked) : task.blocked != null) {
      return false;
    }
    if (column_id != null ? !column_id.equals(task.column_id) : task.column_id != null) {
      return false;
    }
    if (creator_id != null ? !creator_id.equals(task.creator_id) : task.creator_id != null) {
      return false;
    }
    if (description != null ? !description.equals(task.description) : task.description != null) {
      return false;
    }
    if (estimate_id != null ? !estimate_id.equals(task.estimate_id) : task.estimate_id != null) {
      return false;
    }
    if (moved_at != null ? !moved_at.equals(task.moved_at) : task.moved_at != null) {
      return false;
    }
    if (owner_id != null ? !owner_id.equals(task.owner_id) : task.owner_id != null) {
      return false;
    }
    if (position != null ? !position.equals(task.position) : task.position != null) {
      return false;
    }
    if (ready_to_pull != null ? !ready_to_pull.equals(task.ready_to_pull) : task.ready_to_pull != null) {
      return false;
    }
    if (task_type_id != null ? !task_type_id.equals(task.task_type_id) : task.task_type_id != null) {
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
    result = 31 * result + (task_type_id != null ? task_type_id.hashCode() : 0);
    result = 31 * result + (column_id != null ? column_id.hashCode() : 0);
    result = 31 * result + (creator_id != null ? creator_id.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (estimate_id != null ? estimate_id.hashCode() : 0);
    result = 31 * result + (owner_id != null ? owner_id.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    result = 31 * result + (Integer != null ? Integer.hashCode() : 0);
    result = 31 * result + (ready_to_pull != null ? ready_to_pull.hashCode() : 0);
    result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
    result = 31 * result + (moved_at != null ? moved_at.hashCode() : 0);
    return result;
  }
}

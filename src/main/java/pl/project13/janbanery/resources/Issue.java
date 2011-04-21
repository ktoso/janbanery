package pl.project13.janbanery.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Issue extends KanbaneryResource implements Serializable {
  String  url; // yes	on create and update	URL pointing to the issue in an external bug-tracking system
  Integer task_id; // no	 	Task
  Boolean resolved; // no	on create and update	If it was already resolved

  public Issue() {
  }

  public String getUrl() {

    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getTask_id() {
    return task_id;
  }

  public void setTask_id(Integer task_id) {
    this.task_id = task_id;
  }

  public Boolean getResolved() {
    return resolved;
  }

  public void setResolved(Boolean resolved) {
    this.resolved = resolved;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Issue issue = (Issue) o;

    if (created_at != null ? !created_at.equals(issue.created_at) : issue.created_at != null) {
      return false;
    }
    if (resolved != null ? !resolved.equals(issue.resolved) : issue.resolved != null) {
      return false;
    }
    if (task_id != null ? !task_id.equals(issue.task_id) : issue.task_id != null) {
      return false;
    }
    if (type != null ? !type.equals(issue.type) : issue.type != null) {
      return false;
    }
    if (updated_at != null ? !updated_at.equals(issue.updated_at) : issue.updated_at != null) {
      return false;
    }
    if (url != null ? !url.equals(issue.url) : issue.url != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = url != null ? url.hashCode() : 0;
    result = 31 * result + (task_id != null ? task_id.hashCode() : 0);
    result = 31 * result + (resolved != null ? resolved.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

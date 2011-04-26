package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Issue extends KanbaneryResource implements Serializable {

  @Required
  @Settable(On.CreateOrUpdate)
  private String url; // URL pointing to the issue in an external bug-tracking system

  @SerializedName("task_id")
  private Integer taskId; // Task

  @Settable(On.CreateOrUpdate)
  private Boolean resolved; // If it was already resolved

  public Issue() {
  }

  @Override
  public String getResourceId() {
    return "issues";
  }

  public String getUrl() {

    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
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

    if (createdAt != null ? !createdAt.equals(issue.createdAt) : issue.createdAt != null) {
      return false;
    }
    if (resolved != null ? !resolved.equals(issue.resolved) : issue.resolved != null) {
      return false;
    }
    if (taskId != null ? !taskId.equals(issue.taskId) : issue.taskId != null) {
      return false;
    }
    if (type != null ? !type.equals(issue.type) : issue.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(issue.updatedAt) : issue.updatedAt != null) {
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
    result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
    result = 31 * result + (resolved != null ? resolved.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

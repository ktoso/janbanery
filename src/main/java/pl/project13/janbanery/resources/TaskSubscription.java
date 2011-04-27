package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;
import pl.project13.janbanery.resources.additions.ReadOnly;

/**
 * <p>
 * Notice:
 * None of this entities properties are directly settable.
 * When creating a subscription, task_id is taken from the URL and user_id is the id of API_TOKEN owner.
 * </p>
 *
 * @author Konrad Malawski
 */
public class TaskSubscription extends KanbaneryResource {

  @ReadOnly
  @SerializedName("task_id")
  Long taskId;

  @ReadOnly
  @SerializedName("user_id")
  Long userId;

  public TaskSubscription() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getResourceId() {
    return "subscription";
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  @SuppressWarnings({"RedundantIfStatement"})
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

    TaskSubscription that = (TaskSubscription) o;

    if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) {
      return false;
    }
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("TaskSubscription");
    sb.append("{taskId=").append(taskId);
    sb.append(", userId=").append(userId);
    sb.append('}');
    return sb.toString();
  }
}

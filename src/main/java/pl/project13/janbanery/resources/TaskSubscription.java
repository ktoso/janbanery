/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;
import pl.project13.janbanery.resources.additions.ReadOnly;

/**
 * A task subscription is when you get notified about a tasks status.
 * <p/>
 * Notice:
 * None of this entities properties are directly settable.
 * When creating a subscription, task_id is taken from the URL and user_id is the id of API_TOKEN owner.
 *
 * @author Konrad Malawski
 */
public class TaskSubscription extends KanbaneryResource {

  /**
   * Id of the subscribed task
   */
  @ReadOnly
  @SerializedName("task_id")
  Long taskId;

  /**
   * Id of the user who subscribed to the {@link #taskId} task
   */
  @ReadOnly
  @SerializedName("user_id")
  Long userId;

  public TaskSubscription() {
  }

  public TaskSubscription(Long taskId) {
    this.taskId = taskId;
  }

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

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
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * A @{link Task} may contain @{link SubTask}s. A {@link SubTask} is a small chunk of text which may be marked as done.
 * It can be used as super simple to-do list etc.
 *
 * @author Konrad Malawski
 */
public class SubTask extends KanbaneryResource implements Serializable {


  /**
   * The Subtasks id
   */
  @ReadOnly
  private Long id;

  /**
   * Short description of the subtask
   */
  @Required
  @Settable(On.CreateOrUpdate)
  private String body;

  /**
   * If of the "parent task"
   */
  @ReadOnly
  @SerializedName("task_id")
  private Long taskId;

  /**
   * Id of the user who created this {@link SubTask}
   */
  @ReadOnly
  @SerializedName("creator_id")
  private Long creatorId;

  /**
   * True if it is completed, false otherwise
   */
  @Settable(On.CreateOrUpdate)
  private Boolean completed;

  public SubTask() {
  }

  public SubTask(String body) {
    this.body = body;
  }

  @Override
  public String getResourceId() {
    return "subtask";
  }

  public Long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Long getTaskId() {
    return taskId;
  }

  public Long getCreatorId() {
    return creatorId;
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
    if (!super.equals(o)) {
      return false;
    }

    SubTask subTask = (SubTask) o;

    if (body != null ? !body.equals(subTask.body) : subTask.body != null) {
      return false;
    }
    if (completed != null ? !completed.equals(subTask.completed) : subTask.completed != null) {
      return false;
    }
    if (creatorId != null ? !creatorId.equals(subTask.creatorId) : subTask.creatorId != null) {
      return false;
    }
    if (id != null ? !id.equals(subTask.id) : subTask.id != null) {
      return false;
    }
    if (taskId != null ? !taskId.equals(subTask.taskId) : subTask.taskId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (body != null ? body.hashCode() : 0);
    result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
    result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
    result = 31 * result + (completed != null ? completed.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("SubTask");
    sb.append("{id=").append(id);
    sb.append(", body='").append(body).append('\'');
    sb.append(", taskId=").append(taskId);
    sb.append(", creatorId=").append(creatorId);
    sb.append(", completed=").append(completed);
    sb.append('}');
    return sb.toString();
  }
}

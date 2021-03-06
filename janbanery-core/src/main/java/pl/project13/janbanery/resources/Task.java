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
import org.joda.time.DateTime;
import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;
import java.util.Date;

/**
 * A task is the most important thing in Kanbanery, it specifies some chunk of work.
 * It can be assigned, subscribed to and move though columns etc.
 *
 * @author Konrad Malawski
 */
public class Task extends KanbaneryResource implements Serializable {

  /**
   * Id of this task
   */
  @ReadOnly
  private Long id;

  /**
   * The tasks title
   */
  @Required
  @Settable(On.CreateOrUpdate)
  private String title;

  /**
   * The task's taskType, ie. "Bug", "Story", here an ID has to be given.
   * Instead of setting this id you can just set task_type_name to "Bug".
   */
  @Required(alternativeTo = "taskTypeName")
  @Settable(On.CreateOrUpdate)
  @SerializedName("task_type_id")
  private Long taskTypeId;

  /**
   * The task's taskType, ie. "Bug", "Story".
   * Instead of setting this name you can set task_type_id to 1337.
   */
  @Required(alternativeTo = "taskTypeId")
  @Settable(On.CreateOrUpdate)
  @SerializedName("task_type_name")
  private String taskTypeName;

  /**
   * The {@link Column} in which this {@link Task} currently is.
   */
  @Required
  @Settable(On.Update)
  @SerializedName("column_id")
  private Long columnId;

  /**
   * Id of the user who created this task.
   */
  @ReadOnly
  @SerializedName("creator_id")
  private Long creatorId;

  /**
   * A short description of the task.
   */
  @Settable(On.CreateOrUpdate)
  private String description;

  /**
   * The estimate - ie. "how much work is this task?"
   */
  @Settable(On.CreateOrUpdate)
  @SerializedName("estimate_id")
  private Long estimateId;

  /**
   * Who is currently assigned to it
   */
  @Settable(On.CreateOrUpdate)
  @SerializedName("owner_id")
  private Long ownerId;

  /**
   * Position in column.
   * 1-based and counting from the top.
   */
  @Settable(On.CreateOrUpdate)
  private Integer position;

  /**
   * Priority  of this task.
   * It's represented by a number of stars on the Kanban board (0, 1 or 2).
   */
  @Settable(On.CreateOrUpdate)
  private Priority priority;

  /**
   * True if task is ready to be pulled, false otherwise
   */
  @Settable(On.CreateOrUpdate)
  @SerializedName("ready_to_pull")
  private Boolean readyToPull;

  /**
   * When this task should be finished
   */
  @Settable(On.CreateOrUpdate)
  private Date deadline;

  /**
   * True if task is blocked by other task(s), false otherwise
   */
  @ReadOnly
  private Boolean blocked;

  /**
   * This date is being updated when it is moved around the Kanban Board.
   */
  @ReadOnly
  @SerializedName("moved_at")
  private DateTime movedAt;

  public Task() {
  }

  @Override
  public String getResourceId() {
    return "task";
  }

  public Task(String title, TaskType taskType) {
    this.title = title;
    this.taskTypeId = taskType.getId();
  }

  public Task(String title, String taskTypeName) {
    this.title = title;
    this.taskTypeName = taskTypeName;
  }

  public Task(String title, Long taskTypeId) {
    this.title = title;
    this.taskTypeId = taskTypeId;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getTaskTypeId() {
    return taskTypeId;
  }

  public void setTaskTypeId(Long taskTypeId) {
    this.taskTypeId = taskTypeId;
  }

  public String getTaskTypeName() {
    return taskTypeName;
  }

  public void setTaskTypeName(String taskTypeName) {
    this.taskTypeName = taskTypeName;
  }

  public Long getColumnId() {
    return columnId;
  }

  public void setColumnId(Long columnId) {
    this.columnId = columnId;
  }

  public Long getCreatorId() {
    return creatorId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getEstimateId() {
    return estimateId;
  }

  public void setEstimateId(Long estimateId) {
    this.estimateId = estimateId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
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

  public DateTime getMovedAt() {
    return movedAt;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  public Date getDeadline() {
    return deadline;
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

    if (id != null ? !id.equals(task.id) : task.id != null) {
      return false;
    }

    return true;
  }

  /**
   * Try as best as you can to guess if this task is in the archive.
   * The kanbanery API does not provide this information directly.
   *
   * @return true if this {@link Task} is in the Archive, false otherwise
   */
  public boolean isArchived() {
    return id != null && columnId != null && position == null;
  }

  /**
   * // todo implement me
   * Try as best as you can to guess if this task is in the IceBox.
   * The kanbanery API does not provide this information directly.
   *
   * @return true if this {@link Task} is in the IceBox, false otherwise
   */
  public boolean isIceBoxed() {
    throw new NotYetImplementedException();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public static class Builder {
    private Task instance;

    public Builder(String title, TaskType taskType) {
      this(title, taskType.getId());
    }

    public Builder(String title, Long taskTypeId) {
      this.instance = new Task(title, taskTypeId);
    }

    public Builder(String title, String taskTypeName) {
      this.instance = new Task(title, taskTypeName);
    }

    public Builder priority(Priority priority) {
      instance.priority = priority;
      return this;
    }

    public Builder deadline(Date deadline) {
      instance.deadline = deadline;
      return this;
    }

    public Builder description(String description) {
      instance.description = description;
      return this;
    }

    public Builder estimateId(Long estimateId) {
      instance.estimateId = estimateId;
      return this;
    }

    /**
     * The position in the column, 1 based.
     *
     * @param position the position to be set
     * @return the same builder instance
     */
    public Builder position(Integer position) {
      instance.position = position;
      return this;
    }

    public Builder estimate(Estimate estimate) {
      instance.estimateId = estimate.getId();
      return this;
    }

    public DeadlineBuilder deadline() {
      return new DeadlineBuilder(this);
    }

    public Task build() {
      return instance;
    }
  }

  public static class DeadlineBuilder {
    private Builder builder;

    public DeadlineBuilder(Builder builder) {
      this.builder = builder;
    }

    public Builder tomorrow() {
      return inDays(1);
    }

    public Builder nextWeek() {
      return inDays(7);
    }

    public Builder inWeeks(Integer weeks) {
      return inDays(weeks * 7);
    }

    public Builder inDays(Integer days) {
      return builder.deadline(new DateTime().plusDays(days).toDate());
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Task");
    sb.append("{id=").append(id);
    sb.append(", title='").append(title).append('\'');
    sb.append(", taskTypeId=").append(taskTypeId);
    sb.append(", creatorId=").append(creatorId);
    sb.append(", ownerId=").append(ownerId);
    sb.append(", readyToPull=").append(readyToPull);
    sb.append(", position=").append(position);
    sb.append(", priority=").append(priority);
    sb.append(", columnId=").append(columnId);
    sb.append('}');
    return sb.toString();
  }
}

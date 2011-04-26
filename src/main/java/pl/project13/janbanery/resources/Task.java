package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Task extends KanbaneryResource implements Serializable {

  @ReadOnly
  private Long id;

  @Required
  private String title; // Title

  @Required(alternativeTo = "taskTypeName")
  @SerializedName("task_type_id")
  private Long taskTypeId; // Task type, ie. "Bug", "Story". Instead of setting this id you can just settask_type_name to "Bug"

  @Required(alternativeTo = "taskTypeId")
  @SerializedName("task_type_name")
  private String taskTypeName;

  @Required
  @Settable(On.Update)
  @SerializedName("column_id")
  private Long columnId;

  @ReadOnly
  @SerializedName("creator_id")
  private Long creatorId;

  @Settable(On.CreateOrUpdate)
  private String description;

  @Settable(On.CreateOrUpdate)
  @SerializedName("estimate_id")
  private Long estimateId; // Estimate

  @SerializedName("owner_id")
  private Long ownerId; // Who is currently assigned to it

  @Settable(On.CreateOrUpdate)
  private Integer position; // Position in column, 1-based

  @Settable(On.CreateOrUpdate)
  private Priority priority; // Priority (0, 1 or 2)

  @Settable(On.CreateOrUpdate)
  @SerializedName("ready_to_pull")
  private Boolean readyToPull; // If task is ready to be pulled

  private Boolean blocked; // If task is blocked by other task(s)

  @ReadOnly
  @SerializedName("moved_at")
  private DateTime movedAt; // On task was moved to currentUser column

  @Override
  public String getResourceId() {
    return "task";
  }

  public Task() {
  }

  public Task(String title) {
    this.title = title;
  }

  public Task(String title, String taskTypeName) {
    this.title = title;
    this.taskTypeName = taskTypeName;
  }

  public Task(String title, Long taskTypeId) {
    this.title = title;
    this.taskTypeId = taskTypeId;
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

  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
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

  public Boolean isReadyToPull() {
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
    if (priority != task.priority) {
      return false;
    }
    if (readyToPull != null ? !readyToPull.equals(task.readyToPull) : task.readyToPull != null) {
      return false;
    }
    if (taskTypeId != null ? !taskTypeId.equals(task.taskTypeId) : task.taskTypeId != null) {
      return false;
    }
    if (taskTypeName != null ? !taskTypeName.equals(task.taskTypeName) : task.taskTypeName != null) {
      return false;
    }
    if (title != null ? !title.equals(task.title) : task.title != null) {
      return false;
    }

    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  // todo generate this using intellij plugin ;-)
  public static class Builder {
    private Task instance;

    public Builder(String title, Long taskTypeId) {
      this.instance = new Task(title, taskTypeId);
    }

    public Builder(String title, String taskTypeName) {
      this.instance = new Task(title, taskTypeName);
    }

    public Builder(String title) {
      this.instance = new Task(title);
    }

    public Builder priority(Priority priority) {
      instance.priority = priority;
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

    public Builder taskTypeId(Long taskTypeId) {
      instance.taskTypeId = taskTypeId;
      return this;
    }

    public Builder taskType(TaskType taskType) {
      instance.taskTypeId = taskType.getId();
      return this;
    }

    public Task build() {
      return instance;
    }
  }
}
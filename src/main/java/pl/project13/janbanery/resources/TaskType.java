package pl.project13.janbanery.resources;

import java.io.Serializable;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class TaskType extends KanbaneryResource implements Serializable {
  String  name; // Name
  Integer colorCode; // Color code
  Integer projectId; // Project to which the task type is assigned
  Integer position; // Position in project's task types list, 1-based

  public TaskType() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getColorCode() {
    return colorCode;
  }

  public void setColorCode(Integer colorCode) {
    this.colorCode = colorCode;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TaskType taskType = (TaskType) o;

    if (colorCode != null ? !colorCode.equals(taskType.colorCode) : taskType.colorCode != null) {
      return false;
    }
    if (created_at != null ? !created_at.equals(taskType.created_at) : taskType.created_at != null) {
      return false;
    }
    if (name != null ? !name.equals(taskType.name) : taskType.name != null) {
      return false;
    }
    if (position != null ? !position.equals(taskType.position) : taskType.position != null) {
      return false;
    }
    if (projectId != null ? !projectId.equals(taskType.projectId) : taskType.projectId != null) {
      return false;
    }
    if (type != null ? !type.equals(taskType.type) : taskType.type != null) {
      return false;
    }
    if (updated_at != null ? !updated_at.equals(taskType.updated_at) : taskType.updated_at != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (colorCode != null ? colorCode.hashCode() : 0);
    result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

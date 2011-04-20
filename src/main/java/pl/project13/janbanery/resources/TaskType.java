package pl.project13.janbanery.resources;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class TaskType implements Serializable {
  String   name; // Name
  Integer  color_code; // Color code
  Integer  project_id; // Project to which the task type is assigned
  Integer  position; // Position in project's task types list, 1-based

  Date created_at; // Creation time
  Date updated_at; // Last update time
  String   type; // Type of this resource, set to "TaskType". Only for JSON responses. In XML responses node name is "task_type".

  public TaskType() {
  }

  public TaskType(String name, Integer color_code, Integer project_id, Integer position, Date created_at, Date updated_at, String type) {
    this.name = name;
    this.color_code = color_code;
    this.project_id = project_id;
    this.position = position;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getColor_code() {
    return color_code;
  }

  public void setColor_code(Integer color_code) {
    this.color_code = color_code;
  }

  public Integer getProject_id() {
    return project_id;
  }

  public void setProject_id(Integer project_id) {
    this.project_id = project_id;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public Date getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(Date updated_at) {
    this.updated_at = updated_at;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TaskType taskType = (TaskType) o;

    if (color_code != null ? !color_code.equals(taskType.color_code) : taskType.color_code != null) return false;
    if (created_at != null ? !created_at.equals(taskType.created_at) : taskType.created_at != null) return false;
    if (name != null ? !name.equals(taskType.name) : taskType.name != null) return false;
    if (position != null ? !position.equals(taskType.position) : taskType.position != null) return false;
    if (project_id != null ? !project_id.equals(taskType.project_id) : taskType.project_id != null) return false;
    if (type != null ? !type.equals(taskType.type) : taskType.type != null) return false;
    if (updated_at != null ? !updated_at.equals(taskType.updated_at) : taskType.updated_at != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (color_code != null ? color_code.hashCode() : 0);
    result = 31 * result + (project_id != null ? project_id.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

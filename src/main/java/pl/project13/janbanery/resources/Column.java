package pl.project13.janbanery.resources;

import pl.project13.janbanery.resources.additions.Required;

import java.io.Serializable;

/**
 * Describes an column in an Kanban board.
 *
 * @author Konrad Malawski
 */
public class Column extends KanbaneryResource implements Serializable {
  @Required
  private String  name; // on create and update	Name
  private Integer project_id; //	Project
  private Boolean fixed; //	If column can be moved
  private Integer capacity; //	on create and update	Capacity (WIP limit)
  private Integer position; //	on create and update	Position on the board, 1-based

  public Column() {
  }

  @Override public String getResourceId() {
    return "columns";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getProjectId() {
    return project_id;
  }

  public void setProjectId(Integer projectId) {
    this.project_id = projectId;
  }

  public Boolean getFixed() {
    return fixed;
  }

  public void setFixed(Boolean fixed) {
    this.fixed = fixed;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
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
    if (!super.equals(o)) {
      return false;
    }

    Column column = (Column) o;

    if (capacity != null ? !capacity.equals(column.capacity) : column.capacity != null) {
      return false;
    }
    if (fixed != null ? !fixed.equals(column.fixed) : column.fixed != null) {
      return false;
    }
    if (name != null ? !name.equals(column.name) : column.name != null) {
      return false;
    }
    if (position != null ? !position.equals(column.position) : column.position != null) {
      return false;
    }
    if (project_id != null ? !project_id.equals(column.project_id) : column.project_id != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (project_id != null ? project_id.hashCode() : 0);
    result = 31 * result + (fixed != null ? fixed.hashCode() : 0);
    result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    return result;
  }

}

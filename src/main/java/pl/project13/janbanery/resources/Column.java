package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.io.Serializable;

/**
 * Describes an column in an Kanban board.
 *
 * @author Konrad Malawski
 */
public class Column extends KanbaneryResource implements Serializable {

  /**
   * Entity id
   */
  @ReadOnly
  private Long id;

  /**
   * Column name
   */
  @Required
  @Settable(On.CreateOrUpdate)
  private String name;

  /**
   * The id of the Project containing this Column
   */
  @SerializedName("project_id")
  @ReadOnly
  private Integer projectId;

  /**
   * True if Column can NOT can be moved, false otherwise
   */
  @ReadOnly
  private Boolean fixed;

  /**
   * The Columns Capacity, AKA "Work In Progress Limit"
   */
  @Settable(On.CreateOrUpdate)
  private Integer capacity;

  /**
   * Position on the board, 1-based
   */
  @Settable(On.CreateOrUpdate)
  private Integer position;

  public Column() {
  }

  @Override
  public String getResourceId() {
    return "column";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
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
    if (id != null ? !id.equals(column.id) : column.id != null) {
      return false;
    }
    if (name != null ? !name.equals(column.name) : column.name != null) {
      return false;
    }
    if (position != null ? !position.equals(column.position) : column.position != null) {
      return false;
    }
    if (projectId != null ? !projectId.equals(column.projectId) : column.projectId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
    result = 31 * result + (fixed != null ? fixed.hashCode() : 0);
    result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    return result;
  }
}
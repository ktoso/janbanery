package pl.project13.janbanery.resources;

import org.joda.time.DateTime;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public abstract class KanbaneryResource {
  protected DateTime createdAt; // Creation time
  protected DateTime updatedAt; // Last update time
  protected String type; // Type of this resource, set to "User". Only for JSON responses. In XML responses node name is "user".

  protected KanbaneryResource() {
  }

  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public DateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(DateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    KanbaneryResource that = (KanbaneryResource) o;

    if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = createdAt != null ? createdAt.hashCode() : 0;
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

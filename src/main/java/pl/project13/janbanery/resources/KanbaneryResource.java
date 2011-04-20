package pl.project13.janbanery.resources;

import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public abstract class KanbaneryResource {
  protected Date   created_at; // Creation time
  protected Date   updated_at; // Last update time
  protected String type; // Type of this resource, set to "User". Only for JSON responses. In XML responses node name is "user".

  protected KanbaneryResource() {
  }

  protected KanbaneryResource(Date created_at, Date updated_at, String type) {
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.type = type;
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

    KanbaneryResource that = (KanbaneryResource) o;

    if (created_at != null ? !created_at.equals(that.created_at) : that.created_at != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;
    if (updated_at != null ? !updated_at.equals(that.updated_at) : that.updated_at != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = created_at != null ? created_at.hashCode() : 0;
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

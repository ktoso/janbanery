package pl.project13.janbanery.resources;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Estimate implements Serializable {
  BigDecimal value; // Numeric value
  String     label; // Label to display
  Integer    project_id; // Project to which the task type is assigned

  Date created_at; // Creation time
  Date updated_at; // Last update time
  String     type; // Type of this resource, set to "Estimate". Only for JSON responses. In XML responses node name is "estimate".

  public Estimate() {
  }

  public Estimate(BigDecimal value, String label, Integer project_id, Date created_at, Date updated_at, String type) {
    this.value = value;
    this.label = label;
    this.project_id = project_id;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.type = type;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getProject_id() {
    return project_id;
  }

  public void setProject_id(Integer project_id) {
    this.project_id = project_id;
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

    Estimate estimate = (Estimate) o;

    if (created_at != null ? !created_at.equals(estimate.created_at) : estimate.created_at != null) return false;
    if (label != null ? !label.equals(estimate.label) : estimate.label != null) return false;
    if (project_id != null ? !project_id.equals(estimate.project_id) : estimate.project_id != null) return false;
    if (type != null ? !type.equals(estimate.type) : estimate.type != null) return false;
    if (updated_at != null ? !updated_at.equals(estimate.updated_at) : estimate.updated_at != null) return false;
    if (value != null ? !value.equals(estimate.value) : estimate.value != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = value != null ? value.hashCode() : 0;
    result = 31 * result + (label != null ? label.hashCode() : 0);
    result = 31 * result + (project_id != null ? project_id.hashCode() : 0);
    result = 31 * result + (created_at != null ? created_at.hashCode() : 0);
    result = 31 * result + (updated_at != null ? updated_at.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

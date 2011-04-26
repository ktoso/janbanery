package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class Estimate extends KanbaneryResource implements Serializable {

  private BigDecimal value; // Numeric value

  private String label; // Label to display

  @SerializedName("project_id")
  private Integer projectId; // Project to which the task type is assigned

  public Estimate() {
  }

  @Override
  public String getResourceId() {
    return "estimates";
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

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer project_id) {
    this.projectId = project_id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Estimate estimate = (Estimate) o;

    if (createdAt != null ? !createdAt.equals(estimate.createdAt) : estimate.createdAt != null) {
      return false;
    }
    if (label != null ? !label.equals(estimate.label) : estimate.label != null) {
      return false;
    }
    if (projectId != null ? !projectId.equals(estimate.projectId) : estimate.projectId != null) {
      return false;
    }
    if (type != null ? !type.equals(estimate.type) : estimate.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(estimate.updatedAt) : estimate.updatedAt != null) {
      return false;
    }
    if (value != null ? !value.equals(estimate.value) : estimate.value != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = value != null ? value.hashCode() : 0;
    result = 31 * result + (label != null ? label.hashCode() : 0);
    result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}

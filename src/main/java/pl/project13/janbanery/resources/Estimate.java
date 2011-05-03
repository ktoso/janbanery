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
import pl.project13.janbanery.resources.additions.ReadOnly;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * An estimate describes "how much work" a task is.
 * You should not treat Estimates as "hours" as such estimates are always off,
 * instead try to think of them as T-Shirt sizes (S, M, L, XL) or fruits (Orange, Coconut, Watermelon).
 * Numeric values are also ok if you really want numbers :-)
 *
 * @author Konrad Malawski
 */
public class Estimate extends KanbaneryResource implements Serializable {

  /**
   * Estimate id
   */
  @ReadOnly
  private Long id;

  /**
   * Numeric value, describing this estimate
   */
  private BigDecimal value;

  /**
   * Label to display on kanban board
   */
  private String label;

  /**
   * Project to which the task type is assigned
   */
  @ReadOnly
  @SerializedName("project_id")
  private Integer projectId;

  public Estimate() {
  }

  @Override
  public String getResourceId() {
    return "estimates";
  }

  public Long getId() {
    return id;
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Estimate");
    sb.append("{id=").append(id);
    sb.append(", value=").append(value);
    sb.append(", label='").append(label).append('\'');
    sb.append(", projectId=").append(projectId);
    sb.append('}');
    return sb.toString();
  }
}
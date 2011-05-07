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

import java.io.Serializable;

/**
 * A {@link Task} can have a {@link TaskType}, which may determine how important or what about it is.
 * For example "Story", "Bug", "Feature" are popular TaskTypes.
 *
 * @author Konrad Malawski
 */
public class TaskType extends KanbaneryResource implements Serializable {

  /**
   * Id of this entity
   */
  private Long id;

  /**
   * Display name of this {@link TaskType}
   */
  private String name;

  /**
   * Color code
   */
  @SerializedName("color_code")
  private Integer colorCode;

  /**
   * Background color of label displayed by Kanbanery
   */
  @SerializedName("background_color")
  private String backgroundColor;

  /**
   * Color of the text on the taskTypes label displayed by Kanbanery
   */
  @SerializedName("text_color")
  private String textColor;

  /**
   * Project to which the task taskType is assigned
   */
  @SerializedName("project_id")
  private Integer projectId;

  /**
   * Position in project's task types list.
   * 1-based.
   */
  private Integer position;

  @Override
  public String getResourceId() {
    return "task_type";
  }

  public TaskType() {
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

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public String getTextColor() {
    return textColor;
  }

  public void setTextColor(String textColor) {
    this.textColor = textColor;
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

    TaskType taskType = (TaskType) o;

    if (backgroundColor != null ? !backgroundColor.equals(taskType.backgroundColor) : taskType.backgroundColor != null) {
      return false;
    }
    if (colorCode != null ? !colorCode.equals(taskType.colorCode) : taskType.colorCode != null) {
      return false;
    }
    if (id != null ? !id.equals(taskType.id) : taskType.id != null) {
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
    if (textColor != null ? !textColor.equals(taskType.textColor) : taskType.textColor != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (colorCode != null ? colorCode.hashCode() : 0);
    result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
    result = 31 * result + (textColor != null ? textColor.hashCode() : 0);
    result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    return result;
  }
}

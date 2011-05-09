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
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

/**
 * A {@link Task} can have comments, these are just small chunks of text.
 *
 * @author Konrad Malawski
 */
public class Comment extends KanbaneryResource {

  /**
   * Id of this comment resource
   */
  @ReadOnly
  private Long id;

  /**
   * The content of the {@link Comment}
   */
  @Required
  @Settable(On.Create)
  private String body;

  /**
   * Id of the task that this comment is about
   */
  @SerializedName("task_id")
  private Long taskId;

  /**
   * Id of the creator of this comment
   */
  @SerializedName("author_id")
  private Long authorId;

  public Comment() {
  }

  public Comment(String body) {
    this.body = body;
  }

  @Override
  public String getResourceId() {
    return "comment";
  }

  public Long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
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

    Comment comment = (Comment) o;

    if (authorId != null ? !authorId.equals(comment.authorId) : comment.authorId != null) {
      return false;
    }
    if (body != null ? !body.equals(comment.body) : comment.body != null) {
      return false;
    }
    if (id != null ? !id.equals(comment.id) : comment.id != null) {
      return false;
    }
    if (taskId != null ? !taskId.equals(comment.taskId) : comment.taskId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (body != null ? body.hashCode() : 0);
    result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
    result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Comment");
    sb.append("{id=").append(id);
    sb.append(", body='").append(body).append('\'');
    sb.append(", taskId=").append(taskId);
    sb.append(", authorId=").append(authorId);
    sb.append('}');
    return sb.toString();
  }
}

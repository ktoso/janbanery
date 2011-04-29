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
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

/**
 * A {@link Task} can have comments, these are just small chunks of text.
 *
 * @author Konrad Malawski
 */
public class TaskComments extends KanbaneryResource {

  /**
   * The content of the {@link TaskComments}
   */
  @Required
  @Settable(On.Create)
  String body;

  /**
   * Id of the task that this comment is about
   */
  @SerializedName("task_id")
  Integer taskId;

  /**
   * Id of the creator of this comment
   */
  @SerializedName("author_id")
  Integer authorId;

  public TaskComments() {
  }

  @Override
  public String getResourceId() {
    return "comments";
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }
}

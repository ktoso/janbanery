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

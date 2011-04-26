package pl.project13.janbanery.resources;

import com.google.gson.annotations.SerializedName;
import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

/**
 * @author Konrad Malawski
 */
public class TaskComments extends KanbaneryResource {

  @Required
  @Settable(On.Create)
  String body;

  @SerializedName("task_id")
  Integer taskId;

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

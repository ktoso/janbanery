package pl.project13.janbanery.resources;

import pl.project13.janbanery.resources.additions.On;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.util.List;

public class TaskSearch extends KanbaneryResource {

  /**
   * Search phrase
   */
  @Required(comment = "is required when task_type_id and owner_id are not set")
  @Settable(On.Create)
  String phrase;

  /**
   * If set, it narrows the search result to tasks of given task type
   */
  @Required(comment = "is required when if phrase and owner_id are not set")
  @Settable(On.Create)
  Long taskTypeId;

  /**
   * If set, it narrows the search result to tasks owned by given user
   */
  @Required(comment = "yes if phrase and task_type_id are not set")
  @Settable(On.Create)
  Long ownerId;

  /**
   * ID of a project to search in.
   * It can't be set, as this is being determined by the URL that we call, not this property
   */
  @ReadOnly
  Integer projectId;

  /**
   * A place within the project to search in(can be 'icebox', 'board' or 'archive')
   * It's being used in the resource URL, example: .../projects/123/board/tasks/search.json
   */
  @Required
  @Settable(On.Create)
  Scope scope;

  /**
   * Found tasks
   */
  @ReadOnly
  List<Task> tasks;

  public TaskSearch() {
  }

  public TaskSearch(Scope scope) {
    this.scope = scope;
  }

  @Override
  public String getResourceId() {
    return "task_search";
  }

  public String getPhrase() {
    return phrase;
  }

  public void setPhrase(String phrase) {
    this.phrase = phrase;
  }

  public Long getTaskTypeId() {
    return taskTypeId;
  }

  public void setTaskTypeId(Long taskTypeId) {
    this.taskTypeId = taskTypeId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public Scope getScope() {
    return scope;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public static enum Scope {
    ICEBOX,
    BOARD,
    ARCHIVE;

    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }
}

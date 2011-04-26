package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.core.flow.TaskMarkFlow;
import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Tasks {

  // commands -----------------------------------------------------------------

  // creation -------------------------

  TaskFlow create(Task task) throws IOException;

  // archive---------------------------

  void archive(Task task) throws IOException;

  void archiveAllInLastColumn() throws IOException;

  // deletes --------------------------

  void delete(Task task) throws IOException;

  // task movement --------------------

  TaskMoveFlow move(Task task);

  TaskFlow move(Task task, TaskLocation location) throws IOException;

  TaskFlow move(Task task, Column column) throws IOException;

  // state changes --------------------

  TaskFlow update(Task task, Task newValues) throws IOException;

  TaskMarkFlow mark(Task task);

  TaskFlow markAsReadyToPull(Task task) throws IOException;

  TaskFlow markAsNotReadyToPull(Task task) throws IOException;

  // queries ------------------------------------------------------------------

  // kanban board ---------------------
  List<Task> all() throws IOException;

  List<Task> allIn(Column column) throws IOException;

  List<Task> byTitle(String taskTitle) throws IOException;

  List<Task> byTitleIgnoreCase(String taskTitle) throws IOException;

  Task byId(Long taskId) throws IOException;

  List<Task> assignedToMe();

  List<Task> assignedTo(User user) throws IOException;

  List<Task> withPriority(Priority priority) throws IOException;

}
package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Tasks {

  // commands -----------------------------------------------------------------

  // creation -------------------------

  TaskFlow create(Task task) throws IOException, ExecutionException, InterruptedException;

  TaskFlow createInIcebox(Task task) throws IOException;

  // archive---------------------------

  void archive(Task task) throws IOException, ExecutionException, InterruptedException;

  void archiveAllInLastColumn() throws IOException;

  // deletes --------------------------

  void delete(Task task) throws IOException, ExecutionException, InterruptedException;

  // task movement --------------------

  TaskMoveFlow move(Task task);

  TaskFlow move(Task task, TaskLocation location);

  TaskMoveFlow markNotReadyToPull(Task task);

  TaskMoveFlow markReadyToPull(Task task);

  // queries ------------------------------------------------------------------

  List<Task> all() throws IOException, ExecutionException, InterruptedException;

  List<Task> byTitle(String taskTitle) throws IOException;

  List<Task> assignedToMe();

  List<Task> assignedTo(User user);

  List<Task> withPriority(Priority priority);

  Task byId(Long taskId) throws IOException;

  List<Task> byTitleIgnoreCase(String taskTitle) throws IOException;
}
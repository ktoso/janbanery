package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Tasks {

  // commands

  void create(Task task) throws IOException, ExecutionException, InterruptedException;

  void createInIcebox(Task task);

  TaskMoveFlow move(Task task);

  // queries

  List<Task> all();

  List<Task> assignedToMe();

  List<Task> assignedTo(User user);

  List<Task> withPriority(Priority priority);
}
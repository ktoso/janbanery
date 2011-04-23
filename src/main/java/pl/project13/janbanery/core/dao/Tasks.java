package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;

import java.util.List;

public interface Tasks {

  // commands

  void create(Task task);

  void createInIcebox(Task task);

  TaskMoveFlow move(Task task);

  // queries

  List<Task> all();

  List<Task> assignedToMe();

  List<Task> forUser(User user);

  List<Task> withPriority(Priority priority);
}
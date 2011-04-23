package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.util.List;

public interface Tasks {

  // commands

  void create(Task task);

  void createInIcebox(Task task);

  // todo super awesome idea move(task).toColumn(242)
  // todo super awesome idea move(task).toIcebox()
  // todo super awesome idea move(task).toArchive()
  TaskMoveFlow move(Task task);

  // queries

  List<Task> all();

  List<Task> assignedToMe();

  List<Task> forUser(User user);

  List<Task> withPriority(Priority priority);
}
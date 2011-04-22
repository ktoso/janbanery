package pl.project13.janbanery.core;

import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;

import java.util.List;

public interface Tasks {
  List<Task> all();

  List<Task> forUser(User user);

  List<Task> withPriority(Priority priority);
}
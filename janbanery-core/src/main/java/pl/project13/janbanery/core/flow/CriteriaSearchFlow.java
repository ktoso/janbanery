package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.User;

import java.util.List;

public interface CriteriaSearchFlow {
  CriteriaSearchFlow contain(String phrase);

  CriteriaSearchFlow areAssignedTo(User owner);

  CriteriaSearchFlow areOfType(TaskType taskType);

  List<Task> search();
}

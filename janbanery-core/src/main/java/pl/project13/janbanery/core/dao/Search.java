package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.CriteriaSearchFlow;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSearch;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.User;

import java.util.List;

public interface Search {

  Search.ForTasks forTasksIn(TaskSearch.Scope scope);

  public static interface ForTasks {

//    ForTasks inScope(TaskSearch.Scope scope);

    CriteriaSearchFlow that();

    List<Task> containing(String phrase);

    List<Task> assignedTo(User owner);

    List<Task> withTaskType(TaskType taskType);

    List<Task> withTaskType(Long taskTypeId);
  }
}

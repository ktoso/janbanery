package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.TaskType;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface TaskTypes {

  // commands -----------------------------------------------------------------

  TaskType create(TaskType taskType) throws IOException;

  TaskType update(TaskType taskType, TaskType newValues) throws IOException;

  // queries ------------------------------------------------------------------

  List<TaskType> all() throws IOException;

  TaskType any() throws IOException;
}

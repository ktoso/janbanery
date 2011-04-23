package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.TaskType;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface TaskTypes {

  // queries

  List<TaskType> all() throws ExecutionException, InterruptedException, IOException;

  TaskType any() throws IOException, ExecutionException, InterruptedException;
}

package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface TaskMoveFlow extends KanbaneryFlow {
  TaskMoveFlow readyToPull() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow notReadyToPull() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toIceBox() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toNextColumn() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toPreviousColumn() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toArchive() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow to(TaskLocation location) throws IOException, ExecutionException, InterruptedException;
}

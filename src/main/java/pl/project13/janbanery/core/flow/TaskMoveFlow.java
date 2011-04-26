package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface TaskMoveFlow extends KanbaneryFlow<Task> {

  TaskMoveFlow toIceBox() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toNextColumn() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toPreviousColumn() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toArchive() throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow to(TaskLocation location) throws IOException, ExecutionException, InterruptedException;

  TaskMoveFlow toBoard() throws IOException, ExecutionException, InterruptedException;
}

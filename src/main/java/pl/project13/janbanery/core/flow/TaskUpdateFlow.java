package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Estimate;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskType;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface TaskUpdateFlow extends KanbaneryFlow<Task> {

  TaskUpdateFlow type(TaskType taskType) throws IOException;

  TaskUpdateFlow estimate(Estimate estimate) throws IOException;
}

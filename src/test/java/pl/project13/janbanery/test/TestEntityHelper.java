package pl.project13.janbanery.test;

import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

import static pl.project13.janbanery.test.TestConstants.TASK_TITLE;

/**
 * @author Konrad Malawski
 */
public class TestEntityHelper {

  public static Task createTestTask(Janbanery janbanery) throws IOException {
    return new Task.Builder(TASK_TITLE)
        .taskType(janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.LOW)
        .build();
  }

  public static void deleteTestTask(Janbanery janbanery) throws IOException {
    List<Task> tasks = janbanery.tasks().byTitle(TASK_TITLE);
    if (tasks.size() > 0) {
      Task task = tasks.get(0);
      janbanery.tasks().delete(task);
    }
  }
}

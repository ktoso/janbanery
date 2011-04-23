package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Tasks;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This flow enables the API to fluently move tasks around the Kanban board, making it as fun and powerful as possible.
 *
 * @author Konrad Malawski
 */
public class TaskMoveFlow implements KanbaneryFlow {

  private Tasks tasks;

  public TaskMoveFlow(Tasks tasks) {
    this.tasks = checkNotNull(tasks);
  }
}

package pl.project13.janbanery.core.flow;

/**
 * A {@link KanbaneryFlow} is used to enable Fluent Interaction with Kanbanery entities.
 * For example they are used to move tasks from column to column etc.
 * This Interface is just a <strong>marker interface</strong> (for now).
 * <p/>
 * All {@link KanbaneryFlow} interfaces MUST allow retrieval of the underlying entity.
 *
 * @author Konrad Malawski
 */
public interface KanbaneryFlow<T> {

  /**
   * Any KanbaneryFlow MUST allow the user to get the underlying entity object.
   * For example, while performing move() actions on a task, you must be able to
   * get this task object back at aby given moment.
   *
   * @return the underlying entity object for this Flow
   */
  T get();

}

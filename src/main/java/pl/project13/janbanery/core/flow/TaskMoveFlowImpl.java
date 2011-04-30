/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.exceptions.kanbanery.InternalServerErrorKanbaneryException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.TaskAlreadyInLastColumnException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;

/**
 * This flow enables the API to fluently move tasks around the Kanban board, making it as fun and powerful as possible.
 *
 * @author Konrad Malawski
 */
public class TaskMoveFlowImpl implements TaskMoveFlow {

  private Columns columns;
  private Tasks   tasks;
  private Task    task;

  public TaskMoveFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.columns = columns;
    this.tasks = tasks;
    this.task = task;
  }

  @Override
  public TaskMoveFlow toIceBox() throws IOException, CanOnlyIceBoxTaskFromFirstColumnException {
    return to(TaskLocation.ICEBOX);
  }

  @Override
  public TaskMoveFlow toBoard() throws IOException {
    return to(TaskLocation.BOARD);
  }

  @Override
  public TaskMoveFlow toLastColumn() throws IOException {
    Column last = columns.last();
    return to(last);
  }

  @Override
  public TaskFlow asTaskFlow() {
    return new TaskFlowImpl(tasks, columns, task);
  }

  @Override
  public TaskMoveFlow toNextColumn() throws IOException {
    TaskMoveFlow moveFlow;
    try {
      moveFlow = to(TaskLocation.NEXT);
    } catch (InternalServerErrorKanbaneryException e) { // this is a bug workaround
      // kanbanery does handle "ArrayIndexOutOfBounds" right for movement to the left,
      // but for movement to the right it fails and throws a 500 Internal Server Error...
      throw new TaskAlreadyInLastColumnException("{position: 'task is already in last column'}", e); // this is a kanbanery bug workaround
    }
    return moveFlow;
  }

  @Override
  public TaskMoveFlow toPreviousColumn() throws IOException {
    return to(TaskLocation.PREVIOUS);
  }

  @Override
  public TaskMoveFlow toArchive() throws IOException {
    return to(TaskLocation.ARCHIVE);
  }

  @Override
  public TaskMoveFlow to(TaskLocation location) throws IOException {
    TaskFlow move = tasks.move(task, location);

    task = move.get();
    return this;
  }

  @Override
  public TaskMoveFlow to(Column column) throws IOException {
    TaskFlow move = tasks.move(task, column);

    task = move.get();
    return this;
  }

  @Override
  public Task get() {
    return task;
  }
}

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
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
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
  private Tasks tasks;
  private Task task;

  public TaskMoveFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.columns = columns;
    this.tasks = tasks;
    this.task = task;
  }

  @Override
  public TaskFlow toIceBox() throws IOException, CanOnlyIceBoxTaskFromFirstColumnException {
    return to(TaskLocation.ICEBOX);
  }

  @Override
  public TaskFlow toBoard() throws IOException {
    return to(TaskLocation.BOARD);
  }

  @Override
  public TaskFlow toLastColumn() throws IOException {
    Column last = columns.last();
    return to(last);
  }

  @Override
  public TaskFlow toNextColumn() throws IOException {
    TaskFlow taskFlow;
    taskFlow = to(TaskLocation.NEXT);
    return taskFlow;
  }

  @Override
  public TaskFlow toPreviousColumn() throws IOException {
    return to(TaskLocation.PREVIOUS);
  }

  @Override
  public TaskFlow toArchive() throws IOException {
    return to(TaskLocation.ARCHIVE);
  }

  @Override
  public TaskFlow to(TaskLocation location) throws IOException {
    TaskFlow move = tasks.move(task, location);

    task = move.get();
    return new TaskFlowImpl(tasks, columns, task);
  }

  @Override
  public TaskFlow to(Column column) throws IOException {
    TaskFlow move = tasks.move(task, column);

    task = move.get();
    return new TaskFlowImpl(tasks, columns, task);
  }

  @Override
  public Task get() {
    return task;
  }
}

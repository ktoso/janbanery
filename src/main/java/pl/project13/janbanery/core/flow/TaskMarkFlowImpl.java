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
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class TaskMarkFlowImpl implements TaskMarkFlow {

  private Columns columns;
  private Tasks   tasks;
  private Task    task;

  public TaskMarkFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.tasks = tasks;
    this.columns = columns;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asReadyToPull() throws IOException {
    TaskFlow taskMoveFlow = tasks.markAsReadyToPull(task);
    task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, columns, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asNotReadyToPull() throws IOException {
    TaskFlow taskMoveFlow = tasks.markAsNotReadyToPull(task);
    task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, columns, task);
  }

  @Override
  public Task get() {
    return task;
  }
}

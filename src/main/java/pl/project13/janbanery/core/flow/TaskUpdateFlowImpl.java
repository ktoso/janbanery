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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.*;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class TaskUpdateFlowImpl implements TaskUpdateFlow {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks   tasks;
  private Columns columns;
  private Task    task;

  public TaskUpdateFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.tasks = tasks;
    this.columns = columns;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow title(String title) throws IOException {
    Task commandObject = new Task();
    commandObject.setTitle(title);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow description(String description) throws IOException {
    Task commandObject = new Task();
    commandObject.setDescription(description);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow assignTo(User user) throws IOException {
    Task commandObject = new Task();
    commandObject.setOwnerId(user.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow position(Integer positionInColumn) throws IOException {
    Task commandObject = new Task();
    commandObject.setPosition(positionInColumn);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow deadline(DateTime deadline) throws IOException {
    Task commandObject = new Task();
    commandObject.setDeadline(deadline);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow priority(Priority priority) throws IOException {
    Task commandObject = new Task();
    commandObject.setPriority(priority);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow type(TaskType taskType) throws IOException {
    Task commandObject = new Task();
    commandObject.setTaskTypeId(taskType.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow estimate(Estimate estimate) throws IOException {
    Task commandObject = new Task();
    commandObject.setEstimateId(estimate.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow move() {
    return new TaskMoveFlowImpl(tasks, columns, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMarkFlow mark() {
    return new TaskMarkFlowImpl(tasks, columns, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Task get() {
    return task;
  }
}

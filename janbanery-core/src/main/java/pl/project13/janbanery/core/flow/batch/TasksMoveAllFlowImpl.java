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

package pl.project13.janbanery.core.flow.batch;

import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class TasksMoveAllFlowImpl implements TasksMoveAllFlow {

  private Tasks tasks;

  private List<Task> allTasks;

  public TasksMoveAllFlowImpl(Tasks tasks, List<Task> allTasks) {
    this.tasks = tasks;
    this.allTasks = allTasks;
  }

  @Override
  public TasksMoveAllFlow toNextColumn() throws ServerCommunicationException {
    for (Task task : allTasks) {
      tasks.move(task).toNextColumn();
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow toPreviousColumn() {
    for (Task task : allTasks) {
      tasks.move(task).toNextColumn();
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow to(TaskLocation location) throws ServerCommunicationException {
    for (Task task : allTasks) {
      tasks.move(task).to(location);
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow to(Column column) {
    for (Task task : allTasks) {
      tasks.move(task).to(column);
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow toIceBox() throws CanOnlyIceBoxTaskFromFirstColumnException {
    for (Task task : allTasks) {
      tasks.move(task).toIceBox();
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow toArchive() {
    for (Task task : allTasks) {
      tasks.move(task).toArchive();
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow toBoard() throws ServerCommunicationException {
    for (Task task : allTasks) {
      tasks.move(task).toBoard();
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow toLastColumn() throws ServerCommunicationException{
    for (Task task : allTasks) {
      tasks.move(task).toLastColumn();
    }

    return this;
  }

  @Override
  public TasksMoveAllFlow toFirstColumn() throws ServerCommunicationException {
    for (Task task : allTasks) {
      tasks.move(task).toFirstColumn();
    }

    return this;
  }

  @Override
  public List<Task> get() {
    return allTasks;
  }
}

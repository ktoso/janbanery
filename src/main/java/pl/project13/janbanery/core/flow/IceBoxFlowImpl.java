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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.core.dao.IceBox;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class IceBoxFlowImpl implements IceBoxFlow {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks tasks;
  private IceBox iceBox;
  private Task task;

  public IceBoxFlowImpl(Tasks tasks, IceBox iceBox, Task task) {
    this.tasks = tasks;
    this.iceBox = iceBox;
    this.task = task;
  }

  @Override
  public TaskMoveFlow moveToBoard() throws IOException {
    return tasks.move(task).toBoard();
  }

  @Override
  public void delete() throws IOException {
    tasks.delete(task);
  }

  @Override
  public IceBoxFlow update(Task newValues) throws IOException {
    Task updatedTask = tasks.update(task, newValues).get();
    return new IceBoxFlowImpl(tasks, iceBox, updatedTask);
  }

  @Override
  public Task get() {
    return task;
  }
}

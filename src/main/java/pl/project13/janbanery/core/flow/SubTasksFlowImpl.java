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

import pl.project13.janbanery.core.dao.SubTasks;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class SubTasksFlowImpl implements SubTasksFlow {

  private SubTasks subTasks;

  private Task task;

  public SubTasksFlowImpl(SubTasks subTasks, Task task) {
    this.subTasks = subTasks;
    this.task = task;
  }

  @Override
  public SubTaskFlow create(SubTask subTask) throws IOException {
    return subTasks.create(task, subTask);
  }

  @Override
  public SubTaskFlow update(SubTask subTask, SubTask newValues) throws IOException {
    return subTasks.update(subTask, newValues);
  }

  @Override
  public void delete(SubTask subTask) {
    subTasks.delete(subTask);
  }

  @Override
  public List<SubTask> all() throws IOException {
    return subTasks.all(task);
  }

  @Override
  public Task get() {
    return task;
  }
}

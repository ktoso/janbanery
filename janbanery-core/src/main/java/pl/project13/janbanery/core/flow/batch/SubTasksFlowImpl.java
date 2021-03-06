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

import pl.project13.janbanery.core.dao.SubTasks;
import pl.project13.janbanery.core.flow.SubTaskFlow;
import pl.project13.janbanery.core.flow.SubTaskMarkFlow;
import pl.project13.janbanery.core.flow.SubTaskMarkFlowImpl;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.util.predicates.CompletedSubTaskPredicate;
import pl.project13.janbanery.util.predicates.NotCompletedSubTaskPredicate;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

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
  public SubTaskFlow create(SubTask subTask) {
    return subTasks.create(task, subTask);
  }

  @Override
  public SubTaskFlow update(SubTask subTask, SubTask newValues) {
    return subTasks.update(subTask, newValues);
  }

  @Override
  public void delete(SubTask subTask) {
    subTasks.delete(subTask);
  }

  @Override
  public SubTaskMarkFlow mark(SubTask subTask) {
    return new SubTaskMarkFlowImpl(subTasks, subTask);
  }

  @Override
  public SubTasksMarkBatchFlow markAll() {
    return new SubTasksMarkBatchFlowImpl(this, task);
  }

  @Override
  public List<SubTask> all() {
    return subTasks.all(task);
  }

  @Override
  public List<SubTask> allCompleted() {
    List<SubTask> all = all();
    Collection<SubTask> completedTasks = filter(all, new CompletedSubTaskPredicate());
    return newArrayList(completedTasks);
  }

  @Override
  public List<SubTask> allNotCompleted() {
    List<SubTask> all = all();
    Collection<SubTask> completedTasks = filter(all, new NotCompletedSubTaskPredicate());
    return newArrayList(completedTasks);
  }

  @Override
  public Task get() {
    return task;
  }

}

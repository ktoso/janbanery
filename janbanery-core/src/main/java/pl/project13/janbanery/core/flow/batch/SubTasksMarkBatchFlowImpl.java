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

import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class SubTasksMarkBatchFlowImpl implements SubTasksMarkBatchFlow {

  private SubTasksFlow subTasksFlow;

  private Task task;

  public SubTasksMarkBatchFlowImpl(SubTasksFlow subTasksFlow, Task task) {
    this.subTasksFlow = subTasksFlow;
    this.task = task;
  }

  @Override
  public SubTasksFlow asCompleted() throws IOException {
    List<SubTask> notCompletedSubTasks = subTasksFlow.allNotCompleted();
    for (SubTask completeMeSubTask : notCompletedSubTasks) {
      subTasksFlow.mark(completeMeSubTask).asCompleted();
    }

    return subTasksFlow;
  }

  @Override
  public SubTasksFlow asNotCompleted() throws IOException {
    List<SubTask> completedSubTasks = subTasksFlow.allCompleted();
    for (SubTask completeMeSubTask : completedSubTasks) {
      subTasksFlow.mark(completeMeSubTask).asNotCompleted();
    }

    return subTasksFlow;
  }

}

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

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class SubTaskMarkFlowImpl implements SubTaskMarkFlow {

  private SubTasks subTasks;

  private SubTask subTask;

  public SubTaskMarkFlowImpl(SubTasks subTasks, SubTask subTask) {
    this.subTasks = subTasks;
    this.subTask = subTask;
  }

  @Override
  public SubTaskFlow asCompleted() throws IOException {
    return asCompleted(true);
  }

  @Override
  public SubTaskFlow asNotCompleted() throws IOException {
    return asCompleted(false);
  }

  private SubTaskFlow asCompleted(boolean isCompleted) throws IOException {
    SubTask commandObject = new SubTask();
    commandObject.setCompleted(isCompleted);

    return subTasks.update(subTask, commandObject);
  }

  @Override
  public SubTask get() {
    return subTask;
  }
}

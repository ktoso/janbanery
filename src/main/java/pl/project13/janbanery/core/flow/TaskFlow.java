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

import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface TaskFlow extends KanbaneryFlow<Task> {

  /**
   * Get the task passed into this flow.
   * If you, for example, just created a task this method will return the fully populated Task object,
   * (along with owner and creation  dates information etc).
   *
   * @return the previously created Task (used by this flow)
   */
  Task get();

  /**
   * If you just queried for a Task, you may delete it right away using this method.
   *
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  void delete() throws IOException;

  /**
   * @return
   */
  TaskMarkFlow mark();

  /**
   * @return
   */
  TaskMoveFlow move();

  TaskAssignmentFlow assign();
}

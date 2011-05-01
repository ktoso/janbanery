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

/**
 * @author Konrad Malawski
 */
public interface IceBoxFlow extends Flow<Task> {
  /**
   * Move this task to the kanban board of your project.
   *
   * @return the TaskMoveFlow, so you may move the task to some other column right away
   * @throws IOException if the server response could not be fetched
   */
  TaskMoveFlow moveToBoard() throws IOException;

  /**
   * Delete this task from the icebox
   *
   * @throws IOException if the server response could not be fetched
   */
  void delete() throws IOException;

  /**
   * You may update this task with a better description etc.
   * The updated entity is the entity currently being used by this flow,
   * not the passed in value, it's id will be ignored.
   *
   * @param newValues the object with the new values to copy into our underlying task
   * @return a IceBoxFlow instance for other task manipulations
   * @throws IOException if the server response could not be fetched
   */
  IceBoxFlow update(Task newValues) throws IOException;
}

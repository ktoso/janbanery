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

import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.*;

import java.io.IOException;
import java.util.Date;

/**
 * @author Konrad Malawski
 */
public interface TaskUpdateFlow extends Flow<Task> {

  TaskUpdateFlow title(String title) throws ServerCommunicationException;

  TaskUpdateFlow description(String description) throws ServerCommunicationException ;

  TaskUpdateFlow assignTo(User user) throws ServerCommunicationException;

  /**
   * Move the task to the specified priority in the column.
   * This number is 1 based and counting from the top, so "`" is "on the top".
   *
   * @param positionInColumn 1 based number where this task should be moved in the column
   * @return
   * @throws IOException
   */
  TaskUpdateFlow position(Integer positionInColumn) throws ServerCommunicationException;

  TaskUpdateFlow deadline(Date deadline) throws ServerCommunicationException;

  TaskUpdateFlow priority(Priority priority) throws ServerCommunicationException;

  TaskUpdateFlow taskType(TaskType taskType) throws ServerCommunicationException;

  TaskUpdateFlow estimate(Estimate estimate) throws ServerCommunicationException ;

  // go to other flows ----------------

  TaskMoveFlow move();

  TaskMarkFlow mark();

}

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

import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface TaskMoveFlow extends Flow<Task> {
  TaskFlow toNextColumn() throws IOException;

  TaskFlow toPreviousColumn() throws IOException;

  TaskFlow to(TaskLocation location) throws IOException;

  TaskFlow to(Column column) throws IOException;

  /**
   * Move this task to the project's <strong>icebox</strong>.
   * <strong>Requirement</strong>: The task MUST be in the FIRST column.
   *
   * @return a TaskMoveOneFlow instance to allow further task operations
   * @throws java.io.IOException if unable to fetch the server response
   * @throws pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException
   *                             if the task is NOT in the first column
   */
  TaskFlow toIceBox() throws IOException, CanOnlyIceBoxTaskFromFirstColumnException;

  /**
   * Move this task to the project's <strong>archive</strong>.
   * <strong>Requirement</strong>: The task MUST be in the LAST column.
   *
   * @return a TaskMoveOneFlow instance to allow further task operations
   * @throws java.io.IOException if unable to fetch the server response
   */
  TaskFlow toArchive() throws IOException;

  /**
   * Move this task to the project's kanban <strong>board</strong>.
   * <strong>Requirement</strong>: The task MUST be in the iceBox OR archive.
   *
   * @return a TaskMoveOneFlow instance to allow further task operations
   * @throws java.io.IOException if unable to fetch the server response
   */
  TaskFlow toBoard() throws IOException;

  TaskFlow toLastColumn() throws IOException;

  TaskFlow toFirstColumn() throws IOException;
}

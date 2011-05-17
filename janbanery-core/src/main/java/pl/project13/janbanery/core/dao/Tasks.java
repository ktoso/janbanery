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

package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.*;
import pl.project13.janbanery.core.flow.batch.TasksMoveAllFlow;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Tasks extends KanbaneryDao<Task> {

  // commands -----------------------------------------------------------------

  // creation -------------------------

  TaskFlow create(Task task) throws ServerCommunicationException;

  // deletes --------------------------

  void delete(Task task) throws ServerCommunicationException;

  // task movement --------------------

  TaskMoveFlow move(Task task);

  TasksMoveAllFlow moveAllFrom(Column column) throws ServerCommunicationException;

  void moveAll(Column srcColumn, Column destColumn) throws ServerCommunicationException;

  TaskFlow move(Task task, TaskLocation location) throws ServerCommunicationException;

  TaskFlow move(Task task, Column column) throws ServerCommunicationException;

  // task assignment ------------------

  TaskAssignmentFlow assign(Task task);

  // state changes --------------------

  TaskUpdateFlow update(Task task);

  TaskFlow update(Task task, Task newValues) throws ServerCommunicationException;

  TaskMarkFlow mark(Task task);

  TaskFlow markAsReadyToPull(Task task) throws ServerCommunicationException;

  TaskFlow markAsNotReadyToPull(Task task) throws ServerCommunicationException;

  // queries ------------------------------------------------------------------

  // kanban board ---------------------
  List<Task> all() throws ServerCommunicationException;

  List<Task> allIn(Column column) throws ServerCommunicationException;

  List<Task> allByTitle(String taskTitle) throws ServerCommunicationException;

  List<Task> allByTitleIgnoreCase(String taskTitle) throws ServerCommunicationException;

  List<Task> allAssignedTo(User user) throws ServerCommunicationException ;

  List<Task> allWithPriority(Priority priority) throws ServerCommunicationException;

  Task byId(Long taskId) throws ServerCommunicationException;

  TaskFlow assign(Task task, User user) throws ServerCommunicationException ;

}
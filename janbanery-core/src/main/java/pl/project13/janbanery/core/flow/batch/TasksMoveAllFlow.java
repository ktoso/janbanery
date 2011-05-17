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

import pl.project13.janbanery.core.flow.Flow;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface TasksMoveAllFlow extends Flow<List<Task>> {
  TasksMoveAllFlow toNextColumn() throws ServerCommunicationException;

  TasksMoveAllFlow toPreviousColumn() throws ServerCommunicationException;

  TasksMoveAllFlow to(TaskLocation location) throws ServerCommunicationException;

  TasksMoveAllFlow to(Column column) throws ServerCommunicationException;

  TasksMoveAllFlow toIceBox() throws CanOnlyIceBoxTaskFromFirstColumnException;

  TasksMoveAllFlow toArchive() throws ServerCommunicationException;

  TasksMoveAllFlow toBoard() throws ServerCommunicationException;

  TasksMoveAllFlow toLastColumn() throws ServerCommunicationException;

  TasksMoveAllFlow toFirstColumn() throws ServerCommunicationException;

}

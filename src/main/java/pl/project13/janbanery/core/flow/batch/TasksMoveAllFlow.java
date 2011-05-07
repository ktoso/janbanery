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
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface TasksMoveAllFlow extends Flow<List<Task>> {
  TasksMoveAllFlow toNextColumn() throws IOException;

  TasksMoveAllFlow toPreviousColumn() throws IOException;

  TasksMoveAllFlow to(TaskLocation location) throws IOException;

  TasksMoveAllFlow to(Column column) throws IOException;

  TasksMoveAllFlow toIceBox() throws IOException, CanOnlyIceBoxTaskFromFirstColumnException;

  TasksMoveAllFlow toArchive() throws IOException;

  TasksMoveAllFlow toBoard() throws IOException;

  TasksMoveAllFlow toLastColumn() throws IOException;

  TasksMoveAllFlow toFirstColumn() throws IOException;

}
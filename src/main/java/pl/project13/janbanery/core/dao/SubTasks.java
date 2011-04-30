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

import pl.project13.janbanery.core.flow.SubTaskFlow;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface SubTasks {

  // commands -----------------------------------------------------------------

  /**
   * Creates a new {@link SubTask} for the given {@link Task}
   *
   * @param subTask the object carrying the data of the subtask to be created
   * @return a subtask flow populated with the newly created subtask
   */
  SubTaskFlow create(SubTask subTask) throws IOException;

  SubTaskFlow update(SubTask subTask, SubTask newValues) throws IOException;

  void delete(SubTask subTask);

  // queries ------------------------------------------------------------------

  List<SubTask> all() throws IOException;
}
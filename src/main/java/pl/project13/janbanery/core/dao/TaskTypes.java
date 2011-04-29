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

import pl.project13.janbanery.resources.TaskType;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface TaskTypes {

  // commands -----------------------------------------------------------------

  TaskType create(TaskType taskType) throws IOException;

  TaskType update(TaskType taskType, TaskType newValues) throws IOException;

  // queries ------------------------------------------------------------------

  List<TaskType> all() throws IOException;

  TaskType any() throws IOException;
}

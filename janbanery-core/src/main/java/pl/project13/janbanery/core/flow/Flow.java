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

/**
 * A {@link Flow} is used to enable Fluent Interaction with Kanbanery entities.
 * For example they are used to move tasks from column to column etc.
 * This Interface is just a <strong>marker interface</strong> (for now).
 * <p/>
 * All {@link Flow} interfaces MUST allow retrieval of the underlying entity.
 *
 * @author Konrad Malawski
 */
public interface Flow<T> {

  /**
   * Any Flow MUST allow the user to get the underlying entity object.
   * For example, while performing move() actions on a task, you must be able to
   * get this task object back at aby given moment.
   *
   * @return the underlying entity object for this Flow
   */
  T get();

}

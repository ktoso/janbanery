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

package pl.project13.janbanery.resources;

import pl.project13.janbanery.util.collections.Collectionz;

/**
 * Task priorities. Currently kanbanery is only supporting 3 levels of priorities - {0, 1, 2}
 * with 0 being the LOW and 2 being the HIGH priority.
 *
 * @author Konrad Malawski
 */
public enum Priority implements KanbaneryEnumResource {
  /**
   * Low priority. "No stars".
   */
  LOW(0),

  /**
   * Medium priority. "One star".
   */
  MEDIUM(1),

  /**
   * High priority. "Two stars".
   */
  HIGH(2);

  private Integer priorityId;

  Priority(Integer priorityId) {
    this.priorityId = priorityId;
  }

  public Integer id() {
    return priorityId;
  }

  @Override
  public String jsonRepresentation() {
    return String.valueOf(priorityId);
  }

  public static Priority fromPriorityId(final Integer id) {
    String notFoundExceptionMessage = "Tried to create priority for priority id = " + id;
    return Collectionz.findOrThrow(Priority.values(),
                                   notFoundExceptionMessage,
                                   new Collectionz.Criteria<Priority>() {
                                     @Override
                                     public boolean matches(Priority priority) {
                                       return priority.priorityId.equals(id);
                                     }
                                   });

  }

}

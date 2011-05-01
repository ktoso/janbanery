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

import pl.project13.janbanery.core.flow.SubTaskFlow;
import pl.project13.janbanery.core.flow.SubTasksFlow;
import pl.project13.janbanery.resources.SubTask;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface SubTasksMarkBatchFlow {

  /**
   * Marks all {@link SubTask}s of given task as <strong>completed</strong>.
   *
   * @return the updated SubTaskFlow associated with the Task backing this flow
   * @throws IOException if the servers response could not be fetched
   */
  SubTasksFlow asCompleted() throws IOException;

  /**
   * Marks all {@link SubTask}s of given task as <strong>not completed</strong>.
   *
   * @return the updated SubTaskFlow associated with the Task backing this flow
   * @throws IOException if the servers response could not be fetched
   */
  SubTasksFlow asNotCompleted() throws IOException;

}

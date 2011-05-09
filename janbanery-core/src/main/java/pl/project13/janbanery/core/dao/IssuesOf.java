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

import pl.project13.janbanery.core.flow.batch.IssuesFlow;
import pl.project13.janbanery.resources.Issue;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface IssuesOf {

  /**
   * Creates the actual instance on which you can work with subtasks of a concrete task
   *
   * @param task task for which to fetch the subtasks
   * @return ready to work SubTasks instance
   */
  IssuesFlow of(Task task);

  void delete(Issue issue) throws IOException;
}

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

package pl.project13.janbanery.util.predicates;

import com.google.common.base.Predicate;
import pl.project13.janbanery.resources.Task;

/**
 * @author Konrad Malawski
 */
public class TaskByTitlePredicate implements Predicate<Task> {

  private String taskTitle;

  public TaskByTitlePredicate(String taskTitle) {
    this.taskTitle = taskTitle;
  }

  @Override
  public boolean apply(Task input) {
    return taskTitle.equals(input.getTitle());
  }
}

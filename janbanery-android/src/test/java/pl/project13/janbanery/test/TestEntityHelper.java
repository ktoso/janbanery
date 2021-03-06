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

package pl.project13.janbanery.test;

import org.junit.Ignore;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.flow.CommentFlow;
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.*;

import java.util.List;

import static pl.project13.janbanery.test.TestConstants.COLUMN_NAME;
import static pl.project13.janbanery.test.TestConstants.TASK_TITLE;

/**
 * @author Konrad Malawski
 */
@Ignore("It's just an util class")
public class TestEntityHelper {

  public static Task createTestTask(Janbanery janbanery) {
    return new Task.Builder(TASK_TITLE, janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.LOW)
        .build();
  }

  public static void deleteTestTask(Janbanery janbanery) {
    List<Task> tasks = janbanery.tasks().allByTitle(TASK_TITLE);
    if (tasks.size() > 0) {
      Task task = tasks.get(0);
      janbanery.tasks().delete(task);
    }
  }

  public static TaskFlow createTestTaskFlow(Janbanery janbanery) {
    Task build = new Task.Builder(TASK_TITLE, janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.LOW)
        .build();
    return janbanery.tasks().create(build);
  }

  public static Column createTestColumn() {
    return new Column.Builder(COLUMN_NAME).build();
  }

  public static void deleteTestColumn(Janbanery janbanery) {
    try {
      List<Column> columns = janbanery.columns().byName(COLUMN_NAME);
      if (columns.size() > 0) {
        for (Column column : columns) {
          janbanery.columns().delete(column);
        }
      }
    } catch (EntityNotFoundException ignore) {
      // that's ok
    }
  }

  public static SubTask createTestSubTask() {
    return createTestSubTask("Some sub task body");
  }

  public static SubTask createTestSubTask(String body) {
    return new SubTask(body);
  }

  public static CommentFlow createTestComment(Janbanery janbanery, Task task) {
    return janbanery.comments()
                    .of(task)
                    .create(new Comment("Comment text"));
  }

  public static Issue createTestIssue() {
    return new Issue("http://jira.project13.pl/task/324345");
  }
}

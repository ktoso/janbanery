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

import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;

/**
 * @author Konrad Malawski
 */
public class TaskAssignmentFlowImpl implements TaskAssignmentFlow
{

   private Tasks tasks;

   private Task task;

   public TaskAssignmentFlowImpl(Tasks tasks, Task task)
   {
      this.tasks = tasks;
      this.task = task;
   }

   @Override
   public TaskFlow to(User user)
   {
      if (user instanceof User.NoOne)
      {
         return toNobody();
      }
      else
      {
         return tasks.assign(task, user);
      }
   }

   @Override
   public TaskFlow toNobody()
   {
      //noinspection NullableProblems
      return tasks.assign(task, null);
   }

   @Override
   public Task get()
   {
      return task;
   }
}

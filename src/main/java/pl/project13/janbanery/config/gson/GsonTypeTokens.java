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

package pl.project13.janbanery.config.gson;

import com.google.gson.reflect.TypeToken;
import pl.project13.janbanery.resources.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class GsonTypeTokens {

  private GsonTypeTokens() {
  }

  // type tokens for generic types

  public static final Type LIST_WORKSPACE = new TypeToken<List<Workspace>>() {
  }.getType();
  public static final Type LIST_USER      = new TypeToken<List<User>>() {
  }.getType();
  public static final Type LIST_TASK_TYPE = new TypeToken<List<TaskType>>() {
  }.getType();
  public static final Type LIST_TASK      = new TypeToken<List<Task>>() {
  }.getType();
  public static final Type LIST_COLUMN    = new TypeToken<List<Column>>() {
  }.getType();
  public static final Type LIST_ESTIMATE  = new TypeToken<List<Estimate>>() {
  }.getType();


  // simple types

  public static final Class<User>             USER              = User.class;
  public static final Class<Task>             TASK              = Task.class;
  public static final Class<TaskSubscription> TASK_SUBSCRIPTION = TaskSubscription.class;
  public static final Class<Column>           COLUMN            = Column.class;
  public static final Class<Estimate>         ESTIMATE          = Estimate.class;
  public static final Class<TaskType>         TASK_TYPE         = TaskType.class;
}

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

import pl.project13.janbanery.core.flow.CommentFlow;
import pl.project13.janbanery.resources.Comment;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Comments extends CommentsOf {

// commands -----------------------------------------------------------------

  /**
   * Creates a new {@link Comment} for the given {@link Task}
   *
   * @param task    the task we want to add this subtask to
   * @param comment the comment we're about to create
   * @return a {@link CommentFlow} populated with the newly created subtask
   * @throws IOException when unable to fetch the servers response
   */
  CommentFlow create(Task task, Comment comment) throws IOException;

  void delete(Comment comment);

  // queries ------------------------------------------------------------------

  List<Comment> all(Task task) throws IOException;
}

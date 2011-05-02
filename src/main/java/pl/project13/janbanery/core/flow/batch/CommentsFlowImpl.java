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

import pl.project13.janbanery.core.dao.Comments;
import pl.project13.janbanery.core.flow.CommentFlow;
import pl.project13.janbanery.core.flow.CommentFlowImpl;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Comment;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class CommentsFlowImpl implements CommentsFlow {

  private Comments comments;

  private Task task;

  public CommentsFlowImpl(Comments comments, Task task) {
    this.comments = comments;
    this.task = task;
  }

  @Override
  public CommentFlow create(Comment comment) throws IOException {
    return comments.create(task, comment);
  }

  @Override
  public CommentFlow byId(Long id) throws IOException {
    Comment foundComment = null;

    for (Comment comment : all()) {
      if (comment.getId().equals(id)) {
        foundComment = comment;
        break;
      }
    }
    if (foundComment == null) {
      throw new EntityNotFoundException("Task with ID: " + task.getId() +
                                            " does not have a " +
                                            "comment with ID: " + id);
    }

    return new CommentFlowImpl(comments, foundComment);
  }

  @Override
  public CommentsFlow deleteAll() throws IOException {
    for (Comment comment : all()) {
      comments.delete(comment);
    }

    return this;
  }

  @Override
  public List<Comment> all() throws IOException {
    return comments.all(task);
  }

  @Override
  public Task get() {
    return task;
  }
}

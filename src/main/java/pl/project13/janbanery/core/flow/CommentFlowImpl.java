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

import pl.project13.janbanery.core.dao.Comments;
import pl.project13.janbanery.resources.Comment;
import pl.project13.janbanery.resources.Task;

/**
 * @author Konrad Malawski
 */
public class CommentFlowImpl implements CommentFlow {

  private Comments comments;

  private Comment comment;

  public CommentFlowImpl(Comments comments, Comment comment) {
    this.comments = comments;
    this.comment = comment;
  }

  @Override
  public void delete() {
    comments.delete(comment);
  }

  @Override
  public Comment get() {
    return comment;
  }
}

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

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.flow.CommentFlow;
import pl.project13.janbanery.core.flow.CommentFlowImpl;
import pl.project13.janbanery.core.flow.batch.CommentsFlow;
import pl.project13.janbanery.core.flow.batch.CommentsFlowImpl;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.resources.Comment;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class CommentsImpl implements Comments {

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;

  public CommentsImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public CommentFlow create(Task task, Comment comment) {
    String url = getCommentsUrl(task);

    Comment createdComment = restClient.doPost(url, comment, GsonTypeTokens.COMMENT);

    return new CommentFlowImpl(this, createdComment);
  }

  @Override
  public void delete(Comment comment) {
    String url = getCommentUrl(comment);

    restClient.doDelete(url);
  }

  @Override
  public List<Comment> all(Task task) {
    String url = getCommentsUrl(task);

    return restClient.doGet(url, GsonTypeTokens.LIST_COMMENT);
  }

  private String getCommentUrl(Comment comment) {
    return conf.getApiUrl(currentWorkspace, "comments", comment.getId());
  }

  private String getCommentsUrl(Task task) {
    return conf.getApiUrl(currentWorkspace, "tasks", task.getId(), "comments");
  }

  public CommentsImpl using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;

    return this;
  }

  @Override
  public CommentsFlow of(Task task) {
    return new CommentsFlowImpl(this, task);
  }
}

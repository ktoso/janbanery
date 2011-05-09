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
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.core.flow.IssueFlow;
import pl.project13.janbanery.core.flow.IssueFlowImpl;
import pl.project13.janbanery.core.flow.batch.IssuesFlow;
import pl.project13.janbanery.core.flow.batch.IssuesFlowImpl;
import pl.project13.janbanery.resources.Issue;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class IssuesImpl implements Issues {

  private Configuration conf;
  private RestClient restClient;
  private Workspace currentWorkspace;

  public IssuesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public IssueFlow create(Task task, Issue issue) throws IOException {
    String url = getIssuesUrl(task);

    Issue createdIssue = restClient.doPost(url, issue, GsonTypeTokens.ISSUE);

    return new IssueFlowImpl(this, createdIssue);
  }

  @Override
  public IssueFlow update(Issue issue, Issue newValues) throws IOException {
    String url = getIssueUrl(issue);

    Issue updatedIssue = restClient.doPut(url, newValues, GsonTypeTokens.ISSUE);

    return new IssueFlowImpl(this, updatedIssue);
  }

  @Override
  public void delete(Issue issue) throws IOException {
    String url = getIssueUrl(issue);

    restClient.doDelete(url);
  }

  @Override
  public List<Issue> all(Task task) throws IOException {
    String url = getIssuesUrl(task);

    List<Issue> issues = restClient.doGet(url, GsonTypeTokens.LIST_ISSUE);

    return issues;
  }

  @Override
  public IssueFlow byId(Long id) throws IOException {
    String url = getIssueUrl(id);

    Issue issue = restClient.doGet(url, GsonTypeTokens.ISSUE);

    return new IssueFlowImpl(this, issue);
  }

  private String getIssueUrl(Issue issue) {
    return getIssueUrl(issue.getId());
  }

  private String getIssueUrl(Long issueId) {
    return conf.getApiUrl(currentWorkspace, "issues", issueId);
  }

  private String getIssuesUrl(Task task) {
    return conf.getApiUrl(currentWorkspace, "tasks", task.getId(), "issues");
  }

  public Issues using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;
    return this;
  }

  @Override
  public IssuesFlow of(Task task) {
    return new IssuesFlowImpl(this, task);
  }
}

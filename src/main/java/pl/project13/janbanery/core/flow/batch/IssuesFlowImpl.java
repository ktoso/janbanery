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

import pl.project13.janbanery.core.dao.Issues;
import pl.project13.janbanery.core.flow.IssueFlow;
import pl.project13.janbanery.resources.Issue;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class IssuesFlowImpl implements IssuesFlow {

  private Issues issues;

  private Task task;

  public IssuesFlowImpl(Issues issues, Task task) {
    this.issues = issues;
    this.task = task;
  }

  @Override
  public IssueFlow create(Issue issue) throws IOException {
    return issues.create(task, issue);
  }

  @Override
  public IssueFlow update(Issue issue, Issue newValues) throws IOException {
    return issues.update(issue, newValues);
  }

  @Override
  public void delete(Issue issue) {
    issues.delete(issue);
  }

  @Override
  public List<Issue> all() throws IOException {
    return issues.all(task);
  }

  @Override
  public IssueFlow byId(Long id) throws IOException {
    return issues.byId(id);
  }
}

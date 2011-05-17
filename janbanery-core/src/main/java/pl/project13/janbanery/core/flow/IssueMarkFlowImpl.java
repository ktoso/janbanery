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

import pl.project13.janbanery.core.dao.Issues;
import pl.project13.janbanery.resources.Issue;

/**
 * @author Konrad Malawski
 */
public class IssueMarkFlowImpl implements IssueMarkFlow {

  private Issues issues;

  private Issue issue;

  public IssueMarkFlowImpl(Issues issues, Issue issue) {
    this.issues = issues;
    this.issue = issue;
  }

  @Override
  public IssueFlow asResolved() {
    return asResolved(true);
  }

  @Override
  public IssueFlow asNotResolved() {
    return asResolved(false);
  }

  private IssueFlow asResolved(boolean isResolved) {
    Issue commandObject = new Issue();
    commandObject.setResolved(isResolved);

    return issues.update(issue, commandObject);
  }

  @Override
  public Issue get() {
    return issue;
  }
}

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

import java.io.IOException;
import java.net.URL;

/**
 * @author Konrad Malawski
 */
public class IssueUpdateFlowImpl implements IssueUpdateFlow {

  private Issues issues;

  private Issue issue;

  public IssueUpdateFlowImpl(Issues issues, Issue issue) {
    this.issues = issues;
    this.issue = issue;
  }

  @Override
  public IssueUpdateFlow url(String url) throws IOException {
    Issue commandObject = new Issue();
    commandObject.setUrl(url);

    issue = issues.update(issue, commandObject).get();

    return this;
  }

  @Override
  public IssueMarkFlow mark() {
    return new IssueMarkFlowImpl(issues, issue);
  }
}

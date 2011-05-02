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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.IssueFlow;
import pl.project13.janbanery.core.flow.batch.IssuesFlow;
import pl.project13.janbanery.core.flow.batch.SubTasksFlow;
import pl.project13.janbanery.resources.Issue;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class IssuesImplTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);

    janbanery.close();
  }

  @Test
  public void shouldCreateIssue() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    IssuesFlow issuesFlow = janbanery.issues().of(task);

    // when
    Issue testIssue = TestEntityHelper.createTestIssue();
    Issue issue = issuesFlow.create(testIssue).get();

    // then
    List<Issue> allIssues = issuesFlow.all();

    assertThat(allIssues).contains(issue);
  }

  @Test
  public void testById() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    IssuesFlow issuesFlow = janbanery.issues().of(task);
    Issue issue = issuesFlow.create(TestEntityHelper.createTestIssue()).get();

    // when
    Long createdTasksId = issue.getId();
    Issue fetchedIssue = janbanery.issues().of(task).byId(createdTasksId).get();

    // then
    assertThat(fetchedIssue.getId()).isEqualTo(issue.getId());
    assertThat(fetchedIssue).isEqualTo(issue);
  }

  @Test
  public void shouldMarsIssueAsResolved() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    IssuesFlow issuesFlow = janbanery.issues().of(task);
    IssueFlow issueFlow = issuesFlow.create(TestEntityHelper.createTestIssue());

    // when
    Issue issue = issueFlow.mark().asResolved().get();

    // then
    assertThat(issue.getResolved()).isTrue();
  }

  @Test
  public void shouldMarsIssueAsNotResolved() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    IssuesFlow issuesFlow = janbanery.issues().of(task);
    IssueFlow issueFlow = issuesFlow.create(TestEntityHelper.createTestIssue());

    // when
    Issue issue = issueFlow.mark().asResolved()
                           .mark().asNotResolved()
                           .get();

    // then
    assertThat(issue.getResolved()).isFalse();
  }

  @Test
  public void shouldUpdateIssueUrl() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    IssuesFlow issuesFlow = janbanery.issues().of(task);
    IssueFlow issueFlow = issuesFlow.create(TestEntityHelper.createTestIssue());

    // when
    String newIssueUrl = "http://jira.project13.pl/tasks/345345345";

    Issue issue = issueFlow.update().url(newIssueUrl).get();

    // then
    assertThat(issue.getResolved()).isFalse(); // double check this, a new tasks should be not resolved
    assertThat(issue.getUrl()).isEqualTo(newIssueUrl);
  }
}

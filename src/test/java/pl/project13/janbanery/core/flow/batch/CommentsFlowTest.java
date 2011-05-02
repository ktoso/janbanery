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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.CommentFlow;
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.resources.Comment;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class CommentsFlowTest {

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
  public void testCreate() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    Task task = taskFlow.get();

    List<Comment> commentsBefore = janbanery.comments().of(task).all();

    // when
    CommentFlow commentFlow = janbanery.comments()
                                       .of(task)
                                       .create(new Comment("Comment text"));
    Comment comment = commentFlow.get();

    // then
    List<Comment> commentsAfter = janbanery.comments().of(task).all();

    assertThat(commentsAfter).isNotEmpty();
    assertThat(commentsAfter.size()).isEqualTo(commentsBefore.size() + 1);
    assertThat(commentsAfter).contains(comment);
  }

  @Test
  public void shouldDeleteComment() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    CommentFlow commentFlow = TestEntityHelper.createTestComment(janbanery, task);

    // when
    commentFlow.delete();

    // then
    List<Comment> commentsAfter = janbanery.comments().of(task).all();

    assertThat(commentsAfter).isEmpty();
  }

  @Test
  public void shouldDeleteAllComments() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    TestEntityHelper.createTestComment(janbanery, task);
    TestEntityHelper.createTestComment(janbanery, task);
    TestEntityHelper.createTestComment(janbanery, task);

    // when
    CommentsFlow commentsFlow = janbanery.comments().of(task).deleteAll();

    // then
    List<Comment> commentsAfter = commentsFlow.all();

    assertThat(commentsAfter).isEmpty();
  }

  @Test
  public void shouldDeleteCommentById() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    Comment commentToBeDeleted = TestEntityHelper.createTestComment(janbanery, task).get();
    TestEntityHelper.createTestComment(janbanery, task);
    TestEntityHelper.createTestComment(janbanery, task);

    CommentsFlow commentsFlow = janbanery.comments().of(task);

    // when
    Long deleteThisId = commentToBeDeleted.getId();
    commentsFlow.byId(deleteThisId).delete();

    // then
    List<Comment> commentsAfter = commentsFlow.all();

    assertThat(commentsAfter.size()).isEqualTo(2);
    assertThat(commentsAfter).excludes(commentToBeDeleted);
  }
}

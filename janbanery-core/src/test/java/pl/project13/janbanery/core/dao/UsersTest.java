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
import pl.project13.janbanery.exceptions.UserNotFoundException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.User;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class UsersTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf).toWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    janbanery.close();
  }

  @Test
  public void testCurrent() throws Exception {
    // when
    User me = janbanery.users().current();

    // then
    assertThat(me.getFirstName()).isEqualTo("Konrad");
  }

  @Test
  public void testAll() throws Exception {
    // when
    List<User> all = janbanery.users().all();

    // then
    assertThat(all).isNotEmpty();
  }

  @Test
  public void shouldFindUserById() throws Exception {
    // given
    User me = janbanery.users().current();
    Long myId = me.getId();

    // when
    User foundUser = janbanery.users().byId(myId);

    // then
    assertThat(foundUser).isEqualTo(me);
  }

  @Test
  public void shouldFindNoOneForNullId() throws Exception {
    // given
    Long noOneId = null;

    // when
    User foundUser = janbanery.users().byId(noOneId);

    // then
    assertThat(foundUser).isEqualTo(new User.NoOne());
  }

  @Test
  public void testAllInProject() throws Exception {
    // given
    Project project = janbanery.projects().all().get(0);
    User me = janbanery.users().current();

    // when
    List<User> usersInProject = janbanery.users().inProject(project);

    // then
    assertThat(usersInProject).isNotEmpty();
    assertThat(usersInProject).onProperty("id").contains(me.getId());
  }

}

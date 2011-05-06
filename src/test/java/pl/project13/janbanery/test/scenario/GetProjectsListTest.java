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

package pl.project13.janbanery.test.scenario;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.test.TestConstants;

import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class GetProjectsListTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    JanbaneryFactory janbaneryFactory = new JanbaneryFactory();
    PropertiesConfiguration configuration = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);

    janbanery = janbaneryFactory.connectUsing(configuration).toWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    janbanery.close();
  }

  @Test
  public void shouldGetAllWorkspaces() throws Exception {
    // when
    List<Workspace> allWorkspaces = janbanery.workspaces().all();

    // then
    assertThat(allWorkspaces).isNotEmpty();
    Collection<Project> projectsInFirstWorkspace = allWorkspaces.get(0).getProjects();
    assertThat(projectsInFirstWorkspace).isNotEmpty();
  }
}

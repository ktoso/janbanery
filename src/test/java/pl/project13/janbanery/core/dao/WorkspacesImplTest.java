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

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.encoders.ReflectionsBodyGenerator;
import pl.project13.janbanery.exceptions.WorkspaceNotFoundException;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.test.TestConstants;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class WorkspacesImplTest {

  Workspaces workspaces;

  AsyncHttpClient asyncHttpClient;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(TestConstants.VALID_CONF_FILE_LOCATION);

    Gson gson = GsonFactory.create();
    asyncHttpClient = new AsyncHttpClient();
    ReflectionsBodyGenerator encodedBodyGenerator = new ReflectionsBodyGenerator();
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, encodedBodyGenerator);

    workspaces = new WorkspacesImpl(conf, restClient);
  }

  @After
  public void tearDown() throws Exception {
    asyncHttpClient.close();
  }

  @Test
  public void shouldFindAll() throws Exception {
    // when
    List<Workspace> all = workspaces.all();

    // then
    assertThat(all).isNotNull();
    assertThat(all).isNotEmpty();
    Workspace firstWorkspace = all.get(0);
    assertThat(firstWorkspace.getId()).isPositive();
    assertThat(firstWorkspace.getName()).isNotEmpty();
  }

  @Test
  public void shouldFindByName() throws Exception {
    // given
    String givenWorkspaceName = TestConstants.EXISTING_WORKSPACE;

    // when
    Workspace workspace = workspaces.byName(givenWorkspaceName);

    // then
    assertThat(workspace).isNotNull();
    assertThat(workspace.getId()).isPositive();
    assertThat(workspace.getName()).isEqualTo(givenWorkspaceName);
  }

  @Test(expected = WorkspaceNotFoundException.class)
  public void shouldNotFindFictionalWorkspace() throws Exception {
    // given
    String givenWorkspaceName = "no such workspace exists in kanbanery";

    // when
    Workspace workspace = workspaces.byName(givenWorkspaceName);

    // then
    assertThat(workspace).isNotNull();
    assertThat(workspace.getId()).isPositive();
    assertThat(workspace.getName()).isEqualTo(givenWorkspaceName);
  }
}

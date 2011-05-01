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
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.test.TestConstants;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class TaskTypesTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    // dependencies
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    Gson gson = GsonFactory.create();
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(TestConstants.EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    janbanery.close();
  }

  @Test
  public void shouldFetchAllTaskTypes() throws Exception {
    // given
    TaskTypes taskTypes = janbanery.taskTypes();

    // when
    List<TaskType> allTaskTypes = taskTypes.all();

    // then
    assertThat(allTaskTypes).isNotNull();
    assertThat(allTaskTypes).isNotEmpty();
  }

  @Test
  public void shouldFetchAnyTaskType() throws Exception {
    // given
    TaskTypes taskTypes = janbanery.taskTypes();

    // when
    TaskType taskType = taskTypes.any();

    // then
    assertThat(taskType).isNotNull();
    assertThat(taskType.getName()).isIn("Bug", "Chore", "Story");
  }

}

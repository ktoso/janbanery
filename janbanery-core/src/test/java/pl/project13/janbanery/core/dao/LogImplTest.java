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

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.xml.ProjectLogEntry;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class LogImplTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf).toWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);

    janbanery.close();
  }

  @Test
  public void shouldFetchAllAvailable() throws Exception {
    // given
    Log log = janbanery.log();

    // when
    List<ProjectLogEntry> all = log.all();

    // then
    assertValidProjectLogEntries(all);
    assertThat(all.size()).isEqualTo(100);
  }

  @Ignore("Test needs to be fixed")
  @Test
  public void shouldFetch10FreshEntries() throws Exception {
    // given
    Log log = janbanery.log();

    int entriesToFetch = 10;
    List<ProjectLogEntry> allItems = log.all();

    // when
    List<ProjectLogEntry> lastTenItems = log.last(entriesToFetch);

    // then
    assertValidProjectLogEntries(lastTenItems);
    assertThat(lastTenItems.size()).isEqualTo(entriesToFetch);

    // they should be on top of the actions list, "the freshest"
    assertThat(allItems).startsWith(lastTenItems);
  }

  @Test
  public void shouldFetchEntriesFresherThanSample() throws Exception {
    // given
    Log log = janbanery.log();

    ProjectLogEntry oldEntry = log.last(1).get(0);

    // when
    Task testTask = TestEntityHelper.createTestTask(janbanery);
    testTask.setTitle("Should Be In Project Log Now " + new Random().nextInt());
    Task sampleTask = janbanery.tasks().create(testTask).get();

    List<ProjectLogEntry> fresherEvents = log.fresherThan(oldEntry);

    // then
    assertValidProjectLogEntries(fresherEvents);
    assertThat(fresherEvents.size()).isEqualTo(1);

    String freshEventTitle = fresherEvents.get(0).getTitle();
    assertThat(freshEventTitle).contains("has created");
    assertThat(freshEventTitle).contains(sampleTask.getTitle());

    // the event should indeed be "after the previous entry"
    DateTime oldEntryDate = oldEntry.getPubDate();
    DateTime freshEntryDate = fresherEvents.get(0).getPubDate();

    assertThat(freshEntryDate.isAfter(oldEntryDate)).isTrue();
  }

  private void assertValidProjectLogEntries(List<ProjectLogEntry> all) {
    assertThat(all).isNotEmpty();

    ProjectLogEntry logEntry = all.get(0);
    assertThat(logEntry.getGuid()).isNotNull();
    assertThat(logEntry.getGuid().getUrl()).startsWith("http");
    assertThat(logEntry.getPubDate()).isNotNull();
    assertThat(logEntry.getDescription()).isNotNull();
    assertThat(logEntry.getTitle()).isNotNull();
  }
}

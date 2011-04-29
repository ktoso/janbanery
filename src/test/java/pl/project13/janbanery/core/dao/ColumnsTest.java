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

import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.exceptions.NotFoundKanbaneryException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.NotFixedColumnCannotBeFirstException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestEntityHelper;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class ColumnsTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestColumn(janbanery);
  }

  @Test(expected = NotFixedColumnCannotBeFirstException.class)
  public void shouldNotCreateBeforeFixedColumn() throws Exception {
    // given
    Column firstColumn = janbanery.columns().first();
    Column newColumn = TestEntityHelper.createTestColumn();

    // when
    janbanery.columns().create(newColumn).before(firstColumn);

    // then, should have thrown
  }

  @Test
  public void shouldCreateBeforeColumn() throws Exception {
    // given
    Column firstColumn = janbanery.columns().first();
    Column secondColumn = janbanery.columns().after(firstColumn);

    Column newColumnData = TestEntityHelper.createTestColumn();

    // when
    Column newColumn = janbanery.columns().create(newColumnData).before(secondColumn);

    // then
    assertThat(newColumn.getName()).isEqualTo(newColumnData.getName());
    assertThat(newColumn.getPosition()).isEqualTo(2);

    secondColumn = janbanery.columns().refresh(secondColumn);
    assertThat(secondColumn.getPosition()).isEqualTo(3);
  }

  @Test
  public void shouldCreateAfterColumn() throws Exception {
    // given
    Column firstColumn = janbanery.columns().first();
    Column newColumnData = TestEntityHelper.createTestColumn();

    // when
    Column newColumn = janbanery.columns()
                                .create(newColumnData)
                                .after(firstColumn);

    // then
    assertThat(newColumn.getName()).isEqualTo(newColumnData.getName());
    assertThat(newColumn.getPosition()).isEqualTo(2);
  }

  @Test
  public void testAfterBeforeRelations() throws Exception {
    // given
    Columns getColumn = janbanery.columns();

    // when
    Column first = getColumn.onPosition(1);
    Column second = getColumn.onPosition(2);

    // then
    assertThat(getColumn.after(first)).isEqualTo(second);
    assertThat(getColumn.before(second)).isEqualTo(first);
  }

  @Test(expected = NotFoundKanbaneryException.class)
  public void shouldDeleteColumn() throws Exception {
    // given
    Column newColumnData = TestEntityHelper.createTestColumn();
    Column newColumn = janbanery.columns().create(newColumnData).afterFirst();

    // when
    janbanery.columns().delete(newColumn);

    janbanery.columns().byId(newColumn.getId());

    // then, should have thrown
  }

  @Test
  public void shouldMoveColumnBeforeOtherColumn() throws Exception {
    // given
    Column firstColumn = janbanery.columns().first();
    Column lastColumn = janbanery.columns().last();

    Column newColumnData = TestEntityHelper.createTestColumn();
    Column newColumn = janbanery.columns()
                                .create(newColumnData)
                                .after(firstColumn);

    // when
    newColumn = janbanery.columns()
                         .move(newColumn)
                         .before(lastColumn)
                         .get();

    // then
    lastColumn = janbanery.columns().refresh(lastColumn);

    assertThat(newColumn.getPosition()).isEqualTo(lastColumn.getPosition() - 1);
  }

  @Test
  public void shouldMoveColumnAfterOtherColumn() throws Exception {
    // given
    Column firstColumn = janbanery.columns().first();
    Column newColumnData = TestEntityHelper.createTestColumn();
    Column newColumn = janbanery.columns()
                                .create(newColumnData)
                                .after(firstColumn);

    // when
    newColumn = janbanery.columns().move(newColumn).after(firstColumn).get();

    // then
    assertThat(newColumn.getPosition()).isEqualTo(2);
  }

  @Test
  public void shouldMoveColumnToPosition() throws Exception {
    // given
    Column newColumnData = TestEntityHelper.createTestColumn();
    Column newColumn = janbanery.columns()
                                .create(newColumnData)
                                .afterFirst();

    // when
    newColumn = janbanery.columns().move(newColumn).toPosition(3).get();

    // then
    assertThat(newColumn.getPosition()).isEqualTo(3);
  }

}
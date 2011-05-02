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
import org.mockito.Mockito;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.exceptions.NotFoundKanbaneryException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.NotFixedColumnCannotBeFirstException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.Collections;

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

    janbanery.close();
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
  public void shouldCreateBeforeLastColumn() throws Exception {
    // given
    Column lastColumn = janbanery.columns().last();
    Integer prevPositionOfLastColumn = lastColumn.getPosition();
    Column newColumnData = TestEntityHelper.createTestColumn();

    // when
    Column newColumn = janbanery.columns().create(newColumnData).beforeLast();

    // then
    assertThat(newColumn.getName()).isEqualTo(newColumnData.getName());
    assertThat(newColumn.getPosition()).isEqualTo(prevPositionOfLastColumn);

    lastColumn = janbanery.columns().refresh(lastColumn);
    assertThat(lastColumn.getPosition()).isEqualTo(prevPositionOfLastColumn + 1);

    Column beforeLastShouldBeOurColumn = janbanery.columns().before(lastColumn);
    assertThat(beforeLastShouldBeOurColumn).isEqualTo(newColumn);
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

  @Test
  public void shouldUpdateColumnName() throws Exception {
    // given
    Column newColumnData = TestEntityHelper.createTestColumn();
    Column column = janbanery.columns().create(newColumnData).afterFirst();

    // when
    String newColumnName = "New Column Name";
    column = janbanery.columns().update(column).name(newColumnName).get();

    // then
    column = janbanery.columns().refresh(column);

    assertThat(column.getName()).isEqualTo(newColumnName);

    // cleanup
    janbanery.columns().delete(column);
  }

  @Test
  public void shouldUpdateColumnCapacity() throws Exception {
    // given
    Column newColumnData = TestEntityHelper.createTestColumn();
    Column column = janbanery.columns().create(newColumnData).afterFirst();

    // when
    Integer newColumnCapacity = 10;
    column = janbanery.columns().update(column).capacity(newColumnCapacity).get();

    // then
    column = janbanery.columns().refresh(column);

    assertThat(column.getCapacity()).isEqualTo(newColumnCapacity);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldThrowIfUnableToFindFirstColumn() throws Exception {
    // given
    Columns columns = Mockito.spy(janbanery.columns());
    Mockito.when(columns.all()).thenReturn(Collections.<Column>emptyList());

    // when
    columns.first();

    // then, should have thrown
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldThrowIfUnableToFindLastColumn() throws Exception {
    // given
    Columns columns = Mockito.spy(janbanery.columns());
    Mockito.when(columns.all()).thenReturn(Collections.<Column>emptyList());

    // when
    columns.last();

    // then, should have thrown
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldThrowIfUnableToFindColumn() throws Exception {
    // given
    Columns columns = Mockito.spy(janbanery.columns());
    Mockito.when(columns.all()).thenReturn(Collections.<Column>emptyList());

    // when
    columns.onPosition(2);

    // then, should have thrown
  }

}
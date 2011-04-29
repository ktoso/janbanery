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

import pl.project13.janbanery.core.dao.ColumnsImpl;
import pl.project13.janbanery.resources.Column;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class ColumnCreateFlowImpl implements ColumnCreateFlow {

  private ColumnsImpl columns;

  private Column column;

  public ColumnCreateFlowImpl(ColumnsImpl columns, Column column) {
    this.columns = columns;
    this.column = column;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column after(Column thatColumn) throws IOException {
    Integer newPosition = thatColumn.getPosition() + 1;
    return onPosition(newPosition);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column afterFirst() throws IOException {
    return after(columns.first());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column before(Column thatColumn) throws IOException {
    Integer newPosition = thatColumn.getPosition();
    return onPosition(newPosition);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column beforeLast() throws IOException {
    return before(columns.last());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column onPosition(Integer position) throws IOException {
    column.setPosition(position);

    return call();
  }

  public Column call() throws IOException {
    return columns.doCreate(column);
  }
}
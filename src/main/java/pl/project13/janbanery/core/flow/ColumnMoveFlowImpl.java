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

import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.resources.Column;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class ColumnMoveFlowImpl implements ColumnMoveFlow {

  private Columns columns;

  private Column column;

  public ColumnMoveFlowImpl(Columns columns, Column column) {
    this.columns = columns;
    this.column = column;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ColumnMoveFlow before(Column thatColumn) throws IOException {
    return toPosition(thatColumn.getPosition());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ColumnMoveFlow after(Column thatColumn) throws IOException {
    return toPosition(thatColumn.getPosition() + 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ColumnMoveFlow toPosition(Integer position) throws IOException {
    column = columns.update(column).position(position).get();
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column get() {
    return column;
  }
}

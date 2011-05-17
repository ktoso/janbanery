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
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Column;

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

  @Override
  public ColumnUpdateFlow afterFirst() {
    return after(columns.first());
  }

  @Override
  public ColumnUpdateFlow after(Column thatColumn) {
    Integer newPosition = thatColumn.getPosition() + 1;
    return onPosition(newPosition);
  }

  @Override
  public ColumnUpdateFlow beforeLast() throws ServerCommunicationException {
    return before(columns.last());
  }

  @Override
  public ColumnUpdateFlow before(Column thatColumn) {
    Integer newPosition = thatColumn.getPosition();
    return onPosition(newPosition);
  }

  @Override
  public ColumnUpdateFlow onPosition(Integer position) {
    column.setPosition(position);

    return call();
  }

  private ColumnUpdateFlow call() {
    column = columns.doCreate(column);
    return new ColumnUpdateFlowImpl(columns, column);
  }
}

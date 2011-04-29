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

import com.google.common.base.Preconditions;
import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.resources.Column;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class ColumnUpdateFlowImpl implements ColumnUpdateFlow {

  private Columns columns;
  private Column  column;

  public ColumnUpdateFlowImpl(Columns columns, Column column) {
    this.columns = columns;
    this.column = column;
  }

  @Override
  public ColumnUpdateFlow name(String name) throws IOException {
    Preconditions.checkNotNull(name);

    Column commandObject = new Column();
    commandObject.setName(name);

    column = columns.update(column, commandObject);

    return this;
  }

  @Override
  public ColumnUpdateFlow capacity(Integer capacity) throws IOException {
    Preconditions.checkNotNull(capacity);

    Column commandObject = new Column();
    commandObject.setCapacity(capacity);

    column = columns.update(column, commandObject);

    return this;
  }

  @Override
  public ColumnUpdateFlow position(Integer position) throws IOException {
    Preconditions.checkNotNull(position);

    Column commandObject = new Column();
    commandObject.setPosition(position);

    column = columns.update(column, commandObject);

    return this;
  }

  @Override
  public Column get() {
    return column;
  }
}

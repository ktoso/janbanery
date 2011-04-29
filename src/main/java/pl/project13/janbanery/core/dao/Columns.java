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

import pl.project13.janbanery.core.flow.ColumnCreateFlow;
import pl.project13.janbanery.core.flow.ColumnMoveFlow;
import pl.project13.janbanery.core.flow.ColumnUpdateFlow;
import pl.project13.janbanery.core.flow.ColumnUpdateFlowImpl;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Column;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Columns {

  // commands -----------------------------------------------------------------

  ColumnCreateFlow create(Column column);

  ColumnUpdateFlow update(Column column);

  ColumnUpdateFlow update(Column column, Column newValues) throws IOException;

  ColumnUpdateFlow update(Long columnId, Column newValues) throws IOException;

  void delete(Column column);

  ColumnMoveFlow move(Column column);

  // queries ------------------------------------------------------------------

  List<Column> all() throws IOException;

  Column first() throws IOException;

  Column last() throws IOException;

  Column byId(Long id) throws IOException;

  Column nextTo(Column column) throws IOException, EntityNotFoundException;

  Column previousTo(Column column) throws IOException, EntityNotFoundException;

  Column onPosition(Integer desiredPosition) throws IOException, EntityNotFoundException;

  Column refresh(Column column) throws IOException;

  List<Column> byName(String name) throws IOException;

}

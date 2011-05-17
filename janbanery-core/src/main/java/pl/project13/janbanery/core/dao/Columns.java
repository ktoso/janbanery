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
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Column;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Columns extends KanbaneryDao<Column> {

  // commands -----------------------------------------------------------------

  ColumnCreateFlow create(Column column);

  ColumnUpdateFlow update(Column column);

  ColumnUpdateFlow update(Column column, Column newValues) throws ServerCommunicationException;

  ColumnUpdateFlow update(Long columnId, Column newValues) throws ServerCommunicationException;

  void delete(Column column) throws ServerCommunicationException;

  ColumnMoveFlow move(Column column);

  // queries ------------------------------------------------------------------

  List<Column> all() throws ServerCommunicationException;

  Column first() throws ServerCommunicationException;

  Column last() throws ServerCommunicationException;

  Column byId(Long id) throws ServerCommunicationException;

  Column after(Column column) throws ServerCommunicationException, EntityNotFoundException;

  Column before(Column column) throws ServerCommunicationException, EntityNotFoundException;

  Column onPosition(Integer desiredPosition) throws ServerCommunicationException, EntityNotFoundException;

  Column refresh(Column column) throws ServerCommunicationException;

  List<Column> byName(String name) throws ServerCommunicationException;

}

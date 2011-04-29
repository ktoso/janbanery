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

import pl.project13.janbanery.resources.Column;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface ColumnCreateFlow {

  // commands -----------------------------------------------------------------

  Column after(Column column) throws IOException;

  /**
   * Shorthand for creating a column after the first column.
   * <p/>
   * Is equivalent to:
   * <code>
   * after(columns.first())
   * </code>
   *
   * @return the newly created column
   * @throws IOException if unable to fetch the servers response
   */
  Column afterFirst() throws IOException;

  Column before(Column column) throws IOException;

  /**
   * Shorthand for creating a column before the last column.
   * <p/>
   * Is equivalent to:
   * <code>
   * before(columns.last())
   * </code>
   *
   * @return the newly created column
   * @throws IOException if unable to fetch the servers response
   */
  Column beforeLast() throws IOException;

  Column onPosition(Integer position) throws IOException;
}

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.core.flow.*;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.util.collections.Collectionz;
import pl.project13.janbanery.util.predicates.ColumnByNamePredicate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static pl.project13.janbanery.util.collections.Collectionz.findOrThrow;

/**
 * @author Konrad Malawski
 */
public class ColumnsImpl implements Columns {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  public ColumnsImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public List<Column> all() throws IOException {
    log.info("Querying for all columns in workspace: '{}' and project: '{}'", currentWorkspace.getName(), currentProject.getName());

    String url = getDefaultUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_COLUMN);
  }

  @Override
  public ColumnCreateFlow create(Column column) {
    return new ColumnCreateFlowImpl(this, column);
  }

  /**
   * Actually perform the create call to Kanbanery, and don't use a flow to build the call.
   * You will most probably want to use the {@link #create(Column)} method most of the time.
   *
   * @param column the column data to be used for the new column
   * @return the freshly created (and populated) column
   * @throws IOException if the server response could not be fetched
   */
  public Column doCreate(Column column) throws IOException {
    String url = getDefaultUrl();
    return restClient.doPost(url, column, GsonTypeTokens.COLUMN);
  }

  @Override
  public Column first() throws IOException {
    return onPosition(1);
  }

  @Override
  public Column last() throws IOException {
    List<Column> columns = all();

    int lastPosition = columns.size();
    return onPosition(lastPosition);
  }

  @Override
  public Column refresh(Column column) throws IOException {
    return byId(column.getId());
  }

  @Override
  public List<Column> byName(final String name) throws IOException {
    Collection<Column> filteredColumns = filter(all(), new ColumnByNamePredicate(name));
    return newArrayList(filteredColumns);
  }

  @Override
  public Column byId(Long id) throws IOException {
    String url = getColumnUrl(id);
    return restClient.doGet(url, GsonTypeTokens.COLUMN);
  }

  @Override
  public Column after(Column column) throws IOException, EntityNotFoundException {
    Integer desiredPosition = column.getPosition() + 1;

    return onPosition(desiredPosition);
  }

  @Override
  public Column before(Column column) throws IOException, EntityNotFoundException {
    Integer desiredPosition = column.getPosition() - 1;

    return onPosition(desiredPosition);
  }

  @Override
  public Column onPosition(final Integer desiredPosition) throws IOException, EntityNotFoundException {
    String notFoundMessage = "Could not find Column with position = " + desiredPosition + ", on this project board.";
    return findOrThrow(all(), notFoundMessage,
                       new Collectionz.Criteria<Column>() {
                         @Override
                         public boolean matches(Column item) {
                           return item.getPosition().equals(desiredPosition);
                         }
                       });
  }

  @Override
  public ColumnUpdateFlow update(Column column) {
    return new ColumnUpdateFlowImpl(this, column);
  }

  @Override
  public ColumnUpdateFlow update(Column column, Column newValues) throws IOException {
    return update(column.getId(), newValues);
  }

  @Override
  public ColumnUpdateFlow update(Long columnId, Column newValues) throws IOException {
    String url = getColumnUrl(columnId);

    Column column = restClient.doPut(url, newValues, GsonTypeTokens.COLUMN);

    return new ColumnUpdateFlowImpl(this, column);
  }

  @Override
  public void delete(Column column) {
    String url = getColumnUrl(column.getId());

    restClient.doDelete(url);
  }

  @Override
  public ColumnMoveFlow move(Column column) {
    return new ColumnMoveFlowImpl(this, column);
  }

  private String getDefaultUrl() {
    return conf.getApiUrl(currentWorkspace, currentProject.getId(), "columns");
  }

  private String getColumnUrl(Long columnId) {
    return conf.getApiUrl(currentWorkspace, "columns", columnId);
  }

  public Columns using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }

}

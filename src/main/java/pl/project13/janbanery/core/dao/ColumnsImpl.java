package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.core.flow.ColumnUpdateFlow;
import pl.project13.janbanery.core.flow.ColumnUpdateFlowImpl;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class ColumnsImpl implements Columns {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;
  private Project   currentProject;

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
  public Column create(Column column) throws IOException {
    String url = getDefaultUrl();
    return restClient.doPost(url, column, GsonTypeTokens.COLUMN);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column first() throws IOException {
    List<Column> columns = all();
    for (Column column : columns) {
      if (column.getPosition() == 1) {
        return column;
      }
    }

    throw new EntityNotFoundException("Could not find FIRST column. That's very weird...");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Column last() throws IOException {
    List<Column> columns = all();

    int lastId = columns.size();
    for (Column column : columns) {
      if (column.getPosition() == lastId) {
        return column;
      }
    }

    throw new EntityNotFoundException("Could not find LAST column. That's very weird...");
  }

  @Override
  public Column byId(Long id) throws IOException {
    String url = getColumnUrl(id);
    return restClient.doGet(url, GsonTypeTokens.COLUMN);
  }

  @Override
  public ColumnUpdateFlow update(Column column) {
    return new ColumnUpdateFlowImpl(this, column);
  }

  @Override
  public Column update(Column column, Column newValues) {
    return update(column.getId(), newValues);
  }

  @Override
  public Column update(Long columnId, Column newValues) {
    throw new NotYetImplementedException(); // todo implement me
  }

  private String getDefaultUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId(), "columns");
  }

  private String getColumnUrl(Long columnId) {
    return conf.getApiUrl(currentWorkspace.getName(), "columns", columnId);
  }

  public Columns using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}

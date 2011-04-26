package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Columns {
  Columns using(Workspace currentWorkspace, Project currentProject);

  List<Column> all() throws IOException;

  Column first() throws IOException;

  Column last() throws IOException;

  Column byId(Long id) throws IOException;

  Column update(Column column, Column newValues);

  Column update(Long columnId, Column newValues);

  Column create(Column column) throws IOException;
}

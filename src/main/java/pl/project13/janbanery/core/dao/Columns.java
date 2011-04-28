package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.ColumnUpdateFlow;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Column;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Columns {

  // queries ------------------------------------------------------------------

  List<Column> all() throws IOException;

  Column first() throws IOException;

  Column last() throws IOException;

  Column byId(Long id) throws IOException;

  Column nextTo(Column column) throws IOException, EntityNotFoundException;

  Column previousTo(Column column) throws IOException, EntityNotFoundException;

  Column onPosition(Integer desiredPosition) throws IOException, EntityNotFoundException ;

  // commands -----------------------------------------------------------------

  ColumnUpdateFlow update(Column column);

  Column update(Column column, Column newValues);

  Column update(Long columnId, Column newValues);

  Column create(Column column) throws IOException;

}

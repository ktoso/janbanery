package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Column;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface ColumnUpdateFlow extends KanbaneryFlow<Column> {

  ColumnUpdateFlow name(String name) throws IOException;

  ColumnUpdateFlow capacity(Integer capacity) throws IOException;

  ColumnUpdateFlow position(Integer position) throws IOException;
}

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

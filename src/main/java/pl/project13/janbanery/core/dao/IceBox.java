package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.core.flow.IceBoxFlow;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface IceBox {

  // commands -----------------------------------------------------------------

  IceBoxFlow create(Task task) throws IOException;

  void delete(Task task) throws IOException;

  // queries ------------------------------------------------------------------

  List<Task> all() throws IOException;

}

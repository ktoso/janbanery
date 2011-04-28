package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Archive {

  List<Task> all() throws IOException;

  boolean contains(Task task) throws IOException;
}

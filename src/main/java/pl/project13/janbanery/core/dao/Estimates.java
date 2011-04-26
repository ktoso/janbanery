package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Estimate;
import pl.project13.janbanery.resources.Project;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Estimates {

  // commands -----------------------------------------------------------------

  // queries ------------------------------------------------------------------
  List<Estimate> all() throws IOException;

  List<Estimate> allIn(Project project) throws IOException;

  Estimate byId(Long id) throws IOException;
}

package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Estimate;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Estimates {

  // commands -----------------------------------------------------------------

  // queries ------------------------------------------------------------------
  List<Estimate> all();
}

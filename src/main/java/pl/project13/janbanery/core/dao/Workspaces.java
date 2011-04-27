package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.exceptions.WorkspaceNotFoundException;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Workspaces {

  /**
   * Queries for all {@link Workspace}s that you have access to.
   *
   * @return all workspaces you have access to
   * @throws java.io.IOException
   */
  List<Workspace> all() throws IOException;

  /**
   * @return
   * @param name
   * @throws java.io.IOException
   * @throws WorkspaceNotFoundException
   */
  Workspace byName(String name) throws IOException, WorkspaceNotFoundException;

}

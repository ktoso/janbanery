package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.xml.ProjectLogEntry;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Log {

  List<ProjectLogEntry> all() throws ServerCommunicationException;

  List<ProjectLogEntry> last(Integer numberOfActions) throws ServerCommunicationException;
}

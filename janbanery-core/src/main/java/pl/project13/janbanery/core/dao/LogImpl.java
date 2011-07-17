package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.auth.Header;
import pl.project13.janbanery.config.xstream.RssConverter;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.core.rest.response.RestClientResponse;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.resources.xml.ProjectLog;
import pl.project13.janbanery.resources.xml.ProjectLogEntry;
import pl.project13.janbanery.resources.xml.RssResource;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 * @since 1.4
 */
public class LogImpl implements Log {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  private RssConverter rssConverter = new RssConverter(); // todo as dependency?

  public LogImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public List<ProjectLogEntry> all() throws ServerCommunicationException {
    String url = getDefaultLogUrl();

    RestClientResponse response = restClient.doGet(url);
    String responseBody = response.getResponseBody();

    ByteArrayInputStream inStream = asByteArrayInputStream(responseBody);

    RssResource.Rss rss = rssConverter.fromXml(inStream);
    ProjectLog projectLog = ProjectLog.from(rss);

    return projectLog.getItems();
  }

  private ByteArrayInputStream asByteArrayInputStream(String responseBody) {
    ByteArrayInputStream inStream;
    try {
      inStream = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new ServerCommunicationException(e);
    }
    return inStream;
  }

  @Override
  public List<ProjectLogEntry> last(Integer numberOfActions) throws ServerCommunicationException {
    List<ProjectLogEntry> all = all();

    ArrayList<ProjectLogEntry> freshestActions = newArrayList(all.subList(0, numberOfActions));

    return freshestActions;
  }

  private String getDefaultLogUrl() {
    Header authHeader = conf.getAuthProvider().getAuthHeader();
    String apiKey = authHeader.getKey();
    Long projectId = currentProject.getId();
    String workspaceName = currentWorkspace.getName();
    return String.format("https://%s.kanbanery.com/projects/%d/log.rss?api_token=%s", workspaceName, projectId, apiKey);
  }

  public Log using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;

    return this;
  }
}

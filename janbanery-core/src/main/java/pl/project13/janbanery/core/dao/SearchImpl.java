package pl.project13.janbanery.core.dao;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.core.flow.CriteriaSearchFlow;
import pl.project13.janbanery.core.flow.CriteriaSearchFlowImpl;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.resources.*;

import java.util.List;

public class SearchImpl implements Search, Search.ForTasks {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  private TaskSearch.Scope scope;

  public SearchImpl(Configuration conf, RestClient restClient) {
    this.restClient = restClient;
    this.conf = conf;
  }

  @Override
  public ForTasks forTasksIn(TaskSearch.Scope scope) {
    this.scope = scope;

    return this;
  }

  @Override
  public CriteriaSearchFlow that() {
    return new CriteriaSearchFlowImpl(this);
  }

  public List<Task> findByCriteria(TaskSearch taskSearch) {
    TaskSearch.Scope searchScope = Optional.fromNullable(taskSearch.getScope()).or(scope);

    Preconditions.checkNotNull(searchScope, "You need to define a scope for any search you perform");

    String url = getDefaultGetUrl(currentProject, searchScope);

    TaskSearch taskSearchResult = restClient.doPost(url, taskSearch, TaskSearch.class);

    return taskSearchResult.getTasks();
  }

  @Override
  public List<Task> containing(String phrase) {
    TaskSearch criteria = new TaskSearch();
    criteria.setPhrase(phrase);

    return findByCriteria(criteria);
  }

  @Override
  public List<Task> assignedTo(User owner) {
    return ownedBy(owner.getId());
  }

  public List<Task> ownedBy(Long ownerId) {
    TaskSearch criteria = new TaskSearch();
    criteria.setOwnerId(ownerId);

    return findByCriteria(criteria);
  }

  @Override
  public List<Task> withTaskType(TaskType taskType) {
    return withTaskType(taskType.getId());
  }

  public List<Task> withTaskType(Long taskTypeId) {
    TaskSearch criteria = new TaskSearch();
    criteria.setTaskTypeId(taskTypeId);

    return findByCriteria(criteria);
  }

  private String getDefaultGetUrl(Project project, TaskSearch.Scope scope) {
    return conf.getApiUrl(currentWorkspace) + "projects/" + project.getId() + "/" + scope + "/tasks/search.json";
  }

  public Search using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;

    return this;
  }
}

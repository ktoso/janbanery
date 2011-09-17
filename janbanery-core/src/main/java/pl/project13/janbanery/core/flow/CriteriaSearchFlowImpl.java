package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.SearchImpl;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSearch;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.User;

import java.util.List;

public class CriteriaSearchFlowImpl implements CriteriaSearchFlow {

  private SearchImpl search;

  private String phrase;
  private User owner;
  private TaskType taskType;

  public CriteriaSearchFlowImpl(SearchImpl search) {
    this.search = search;
  }

  @Override
  public CriteriaSearchFlow contain(String phrase) {
    this.phrase = phrase;

    return this;
  }

  @Override
  public CriteriaSearchFlow areAssignedTo(User owner) {
    this.owner = owner;

    return this;
  }

  @Override
  public CriteriaSearchFlow areOfType(TaskType taskType) {
    this.taskType = taskType;

    return this;
  }

  @Override
  public List<Task> search() {
    TaskSearch taskSearch = new TaskSearch();
    taskSearch.setPhrase(phrase);
    taskSearch.setOwnerId(owner.getId());
    taskSearch.setTaskTypeId(taskType.getId());

    return search.findByCriteria(taskSearch);
  }
}

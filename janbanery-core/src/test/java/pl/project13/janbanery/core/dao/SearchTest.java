package pl.project13.janbanery.core.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSearch;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.User;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

public class SearchTest {

  Janbanery janbanery;

  User me;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf)
                                      .toWorkspace(EXISTING_WORKSPACE)
                                      .usingProject("janbaneryProject");

    me = janbanery.users().current();
  }

  @After
  public void tearDown() throws Exception {
    janbanery.close();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowWhenNoSearchScopeIsDefined() throws Exception {
    // given
    TaskSearch criteria = new TaskSearch();

    // when
    SearchImpl search = (SearchImpl) janbanery.search();
    search.findByCriteria(criteria);

    // should have thrown
    fail("should have thrown exception, due to no search scope");
  }

  @Test
  public void shouldFindTasksContainingAString() throws Exception {
    // given
    String sampleTitle = "TestTask" + System.currentTimeMillis();
    TaskFlow taskFlow = janbanery.tasks().create(new Task(sampleTitle, janbanery.taskTypes().any()));

    janbanery.tasks().allIn(janbanery.columns().first());

    // when
    List<Task> foundTasks = janbanery.search()
                                     .forTasksIn(TaskSearch.Scope.BOARD)
                                     .containing("Test");

    // then
    assertThat(foundTasks).onProperty("title").contains(sampleTitle);

    // and cleanup
    taskFlow.delete();
  }

  @Test
  public void shouldFindTasksAssignedToMe() throws Exception {
    // given
    String sampleTitle = "TestTask" + System.currentTimeMillis();
    TaskFlow taskFlow = janbanery.tasks()
                                 .create(new Task(sampleTitle, janbanery.taskTypes().any()))
                                 .assign().to(me);


    janbanery.tasks().allIn(janbanery.columns().first());

    // when
    List<Task> foundTasks = janbanery.search()
                                     .forTasksIn(TaskSearch.Scope.BOARD)
                                     .assignedTo(me);

    // then
    assertThat(foundTasks).onProperty("title").contains(sampleTitle);
    assertThat(foundTasks).onProperty("ownerId").contains(me.getId());

    // and cleanup
    taskFlow.delete();
  }
  
    @Test
  public void shouldFindTasksWithTaskType() throws Exception {
    // given
    String sampleTitle = "TestTask" + System.currentTimeMillis();
    TaskFlow taskFlow = janbanery.tasks()
                                 .create(new Task(sampleTitle, janbanery.taskTypes().any()));


    janbanery.tasks().allIn(janbanery.columns().first());

    // when
    List<Task> foundTasks = janbanery.search()
                                     .forTasksIn(TaskSearch.Scope.BOARD)
                                     .assignedTo(me);

    // then
    assertThat(foundTasks).onProperty("title").contains(sampleTitle);
    assertThat(foundTasks).onProperty("taskTypeId").contains(me.getId());

    // and cleanup
    taskFlow.delete();
  }

  @Test
  public void shouldFindTasksByCriteria() throws Exception {
    // given
    String sampleTitle = "TestTask" + System.currentTimeMillis();
    TaskType taskType = janbanery.taskTypes().any();

    TaskFlow taskFlow = janbanery.tasks().create(new Task(sampleTitle, taskType))
                                 .assign().to(me);

    janbanery.tasks().allIn(janbanery.columns().first());

    // when
    List<Task> foundTasks = janbanery.search()
                                     .forTasksIn(TaskSearch.Scope.BOARD)
                                     .that()
                                     .contain("Test")
                                     .areAssignedTo(me)
                                     .areOfType(taskType)
                                     .search();

    // then
    assertThat(foundTasks).onProperty("title").contains(sampleTitle);

    // and cleanup
    taskFlow.delete();
  }
}

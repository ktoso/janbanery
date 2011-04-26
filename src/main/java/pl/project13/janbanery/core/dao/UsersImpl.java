package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.exceptions.UserNotFoundException;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class UsersImpl implements Users {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;

  public UsersImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public User current() throws IOException {
    String url = conf.getApiUrl().replace("user/", "user.json"); // todo this sucks

    User user = restClient.doGet(url, GsonTypeTokens.USER);
    return user;
  }

  public Users using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;
    return this;
  }
}


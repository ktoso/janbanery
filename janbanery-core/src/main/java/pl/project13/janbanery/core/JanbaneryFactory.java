/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.DefaultConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.core.rest.AsyncHttpClientRestClient;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.encoders.ReflectionBodyGenerator;
import pl.project13.janbanery.resources.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class JanbaneryFactory {

  private Logger log = LoggerFactory.getLogger(getClass());

  private AsyncHttpClient asyncHttpClient;

  private ReflectionBodyGenerator encodedBodyGenerator = new ReflectionBodyGenerator();
  private Gson gson = GsonFactory.create();

  private RestClient restClient;

  public JanbaneryFactory() {
    asyncHttpClient = new AsyncHttpClient();
  }

  public JanbaneryFactory(AsyncHttpClient asyncHttpClient) {
    this.asyncHttpClient = asyncHttpClient;
  }

  public JanbaneryFactory(RestClient restClient) {
    this.restClient = restClient;
  }

  public JanbaneryToWorkspace connectUsing(Configuration configuration) {
    RestClient restClient = getRestClient(configuration);
    return new JanbaneryToWorkspace(new Janbanery(configuration, restClient));
  }

  /**
   * The <strong>recommended</strong> method for connecting to Kanbanery.
   *
   * @param apiKey your API key for kanbanery, you can find it in your profile settings on kanbanery.com
   * @return a properly setup Janbanery instance using your API key for authentication
   */
  public JanbaneryToWorkspace connectUsing(String apiKey) {
    DefaultConfiguration conf = new DefaultConfiguration(apiKey);
    return connectUsing(conf);
  }

  /**
   * This method will connect to kanbanery via basic user/pass authentication
   * <strong>but will then switch to API Key Mode</strong> to increase the security of communication over the wire.
   *
   * @param user     your kanbanery username (email)
   * @param password your kanbanery password for this user
   * @return an Janbanery instance setup using the API Key auth, which will be fetched during construction
   * @throws IOException
   */
  public JanbaneryToWorkspace connectUsing(String user, String password) throws IOException {
    DefaultConfiguration conf = new DefaultConfiguration(user, password);
    RestClient restClient = getRestClient(conf);
    Janbanery janbanery = new Janbanery(conf, restClient);

    // fetch and switch to apiKey mode
    String apiKey = getCurrentUserApiKey(janbanery);
    conf.forceKeyAuthMode(apiKey);

    return new JanbaneryToWorkspace(janbanery);
  }

  private String getCurrentUserApiKey(Janbanery janbanery) throws IOException {
    User currentUser = janbanery.users().current();
    return currentUser.getApiToken();
  }

  /**
   * This method will connect to kanbanery via basic user/pass authentication
   * <strong>and will keep using it until forced to switch modes!</strong>. This method is not encouraged,
   * you should use {@link JanbaneryFactory#connectUsing(String, String)} and allow Janbanery to switch to apiKey mode
   * as soon as it load's up to increase security over the wire.
   *
   * @param user     your kanbanery username (email)
   * @param password your kanbanery password for this user
   * @return an Janbanery instance setup using the API Key auth, which will be fetched during construction
   * @throws IOException
   */
  public Janbanery connectAndKeepUsing(String user, String password) throws IOException {
    DefaultConfiguration conf = new DefaultConfiguration(user, password);
    RestClient restClient = getRestClient(conf);
    return new Janbanery(conf, restClient);
  }

  private RestClient getRestClient(Configuration configuration) {
    RestClient restClient = this.restClient;

    if (restClient == null) {
      restClient = new AsyncHttpClientRestClient(asyncHttpClient);
    }

    restClient.init(configuration, gson, encodedBodyGenerator);

    return restClient;
  }

  public void setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
    this.asyncHttpClient = asyncHttpClient;
  }

  public void setEncodedBodyGenerator(ReflectionBodyGenerator encodedBodyGenerator) {
    this.encodedBodyGenerator = encodedBodyGenerator;
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }

  /**
   * This is a small "flow", to force the user into telling us what workspace we'll be working on
   * <p/>
   * // todo find a batter name for this
   *
   * @author Konrad Malawski
   */
  public static class JanbaneryToWorkspace {

    private Janbanery janbanery;

    public JanbaneryToWorkspace(Janbanery janbanery) {
      this.janbanery = janbanery;
    }

    public Janbanery toWorkspace(String name) throws IOException, ExecutionException, InterruptedException {
      return janbanery.usingWorkspace(name);
    }

  }
}


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

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import pl.project13.janbanery.exceptions.RestClientException;
import pl.project13.janbanery.exceptions.kanbanery.KanbaneryException;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Konrad Malawski
 */
public interface RestClient {
  void verifyResponseCode(Response response) throws KanbaneryException;

  Response doPost(String url, KanbaneryResource resource) throws IOException;

  @SuppressWarnings("unchecked")
  <T> T doPost(String url, KanbaneryResource resource, Class<?> returnType) throws IOException;

  Response doGet(String url);

  @SuppressWarnings("unchecked")
  <T> T doGet(String url, Type returnType) throws IOException;

  Response doDelete(String url);

  Response doPut(String url, String requestBody);

  @SuppressWarnings({"unchecked"})
  <T> T doPut(String url, String requestBody, Class<?> returnType) throws IOException;

  @SuppressWarnings({"unchecked"})
  <T> T doPut(String url, String requestBody, Type returnType) throws IOException;

  @SuppressWarnings("unchecked")
  <T> T doPut(String url, KanbaneryResource requestObject, Class<?> returnType) throws IOException;

  void authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);

  void setFormUrlEncodedBody(AsyncHttpClient.BoundRequestBuilder requestBuilder, String requestBody);

  Response execute(AsyncHttpClient.BoundRequestBuilder requestBuilder) throws RestClientException;

  void close();
}

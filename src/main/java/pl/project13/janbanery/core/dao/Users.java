package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface Users {
  User current() throws IOException, ExecutionException, InterruptedException;
}

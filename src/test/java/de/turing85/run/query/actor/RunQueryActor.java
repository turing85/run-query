package de.turing85.run.query.actor;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import de.turing85.run.query.RunQueryResource;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;

@ApplicationScoped
public class RunQueryActor {
  private final List<ValidatableResponse> responses = new ArrayList<>();

  public void clearState() {
    responses.clear();
  }

  public void removeTableIfExists(String tableName) {
    // @formatter:off
    RestAssured.given().queryParam("query", "DROP TABLE IF EXISTS %s;".formatted(tableName))
        .when().get(RunQueryResource.PATH).then();
    // @formatter:on
  }

  public void runQuery(String query) {
    // @formatter:off
    responses.add(RestAssured
        .given().queryParam("query", query)
        .when().get(RunQueryResource.PATH).then());
    // @formatter:on
  }

  public void indexHasValue(int index, Response.Status status) {
    ValidatableResponse response = responses.get(index);
    response.statusCode(is(status.getStatusCode()));
  }

  public void allSucceed() {
    for (int index = 0; index < responses.size(); ++index) {
      indexHasValue(index, Response.Status.OK);
    }
  }
}

package de.turing85.run.query.steps;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import de.turing85.run.query.actor.RunQueryActor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class RunQuerySteps {
  private final RunQueryActor actor;

  /**
   * C'tor for CDI.
   */
  @SuppressWarnings("unused")
  RunQuerySteps() {
    this(null);
  }

  @Given("A clean state")
  public void a_clean_state() {
    actor.clearState();
  }

  @Given("Table {string} does not exist")
  public void table_does_not_exist(String tableName) {
    actor.removeTableIfExists(tableName);
  }

  @When("I run the query {string}")
  public void i_run_the_query(String query) {
    actor.runQuery(query);
  }

  @Then("The query succeeds")
  public void the_query_succeeds() {
    actor.indexHasValue(0, Response.Status.OK);
  }

  @Then("All queries succeed")
  public void queries_succeed() {
    actor.allSucceed();
  }

  @Then("The {int}(st)(nd)(rd)(th) query succeeds")
  public void the_ith_query_succeeds(int index) {
    actor.indexHasValue(index - 1, Response.Status.OK);
  }

  @Then("The {int}(st)(nd)(rd)(th) query fails")
  public void the_ith_query_fails(int index) {
    actor.indexHasValue(index - 1, Response.Status.BAD_REQUEST);
  }
}

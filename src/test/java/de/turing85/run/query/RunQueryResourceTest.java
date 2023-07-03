package de.turing85.run.query;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(features = {"src/test/resources/de/turing85/run/query/runQuery.feature"},
    glue = "de.turing85.run.query.steps")
class RunQueryResourceTest extends CucumberQuarkusTest {
  public static void main(String... args) {
    runMain(RunQueryResourceTest.class, args);
  }
}

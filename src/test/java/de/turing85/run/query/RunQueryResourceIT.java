package de.turing85.run.query;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

@CucumberOptions(features = {"src/test/resources/de/turing85/run/query/runQuery.feature"},
    glue = "de.turing85.run.query.steps")
@QuarkusIntegrationTest
@EnableAutoWeld
@AddPackages(RunQueryResourceIT.class)
class RunQueryResourceIT extends CucumberQuarkusTest {
  public static void main(String... args) {
    runMain(RunQueryResourceIT.class, args);
  }
}

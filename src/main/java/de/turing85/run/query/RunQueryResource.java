package de.turing85.run.query;

import java.sql.Connection;
import java.sql.Statement;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;

@Path(RunQueryResource.PATH)
@RequiredArgsConstructor
public class RunQueryResource {
  public static final String PATH = "run-query";

  private final AgroalDataSource dataSource;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Transactional
  public Response runQuery(@Valid @NotNull @QueryParam("query") String query) {
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
      statement.execute(query);
      return Response.status(Response.Status.OK).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }
}

package de.turing85.run.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
  private static final String NO_RESULT_MESSAGE = "No results were returned by the query.";

  private final AgroalDataSource dataSource;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Transactional
  public Response runQuery(@Valid @NotNull @QueryParam("query") String query) {
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
      final ResultSet rs = statement.executeQuery(query);
      return Response.ok(transformToCsv(rs)).build();
    } catch (Exception e) {
      final String message = e.getMessage();
      if (NO_RESULT_MESSAGE.equals(message)) {
        return Response.ok(message).build();
      }
      return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }
  }

  private static String transformToCsv(ResultSet resultSet) throws SQLException {
    StringBuilder response =
        new StringBuilder().append(getColumnNames(resultSet)).append(System.lineSeparator());
    while (resultSet.next()) {
      String values = getColumValues(resultSet);
      response.append(values).append(System.lineSeparator());
    }
    return response.toString();
  }

  private static String getColumnNames(ResultSet resultSet) throws SQLException {
    List<String> columns = new ArrayList<>();
    final ResultSetMetaData metaData = resultSet.getMetaData();
    for (int index = 1; index <= metaData.getColumnCount(); ++index) {
      columns.add(metaData.getColumnName(index));
    }
    return String.join(", ", columns);
  }

  private static String getColumValues(ResultSet resultSet) throws SQLException {
    List<String> columns = new ArrayList<>();
    for (int index = 1; index <= resultSet.getMetaData().getColumnCount(); ++index) {
      columns.add(resultSet.getString(index));
    }
    return String.join(", ", columns);
  }
}

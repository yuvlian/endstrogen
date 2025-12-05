package yuvlian.endstrogen.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Database {
  private static final HikariDataSource dataSource;

  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:sqlite:endstrogen.db");
    config.setMaximumPoolSize(3);
    config.setPoolName("EndPool");
    config.setConnectionTestQuery("SELECT 1");

    dataSource = new HikariDataSource(config);

    try (Connection conn = dataSource.getConnection()) {
      TableHelper.setupTables(conn);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  public static void exec(String sql, Object... params) throws SQLException {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }

      stmt.executeUpdate();
    }
  }

  public static List<Map<String, Object>> fetch(String sql, Object... params) throws SQLException {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }

      try (ResultSet rs = stmt.executeQuery()) {
        List<Map<String, Object>> results = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int columns = meta.getColumnCount();

        while (rs.next()) {
          Map<String, Object> row = new HashMap<>();
          for (int i = 1; i <= columns; i++) {
            row.put(meta.getColumnName(i), rs.getObject(i));
          }
          results.add(row);
        }

        return results;
      }
    }
  }

  public static void close() {
    dataSource.close();
  }
}

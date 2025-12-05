package yuvlian.endstrogen.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import yuvlian.endstrogen.database.tables.Account;

public class TableHelper {
  public static void setupTables(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
      String[] tables = new String[] {Account.getTableQuery()};
      String finalQuery = String.join(";\n", tables);
      stmt.execute(finalQuery);
    }
  }
}

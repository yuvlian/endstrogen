package yuvlian.endstrogen.database.tables;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import yuvlian.endstrogen.database.Database;

public class Account {
  private final int uid;
  private final String username;
  private final String passwordHash;
  private final String dispatchToken;
  private final String comboToken;
  private final boolean isBanned;

  private Account(
      int uid,
      String username,
      String passwordHash,
      String dispatchToken,
      String comboToken,
      boolean isBanned) {
    this.uid = uid;
    this.username = username;
    this.passwordHash = passwordHash;
    this.dispatchToken = dispatchToken;
    this.comboToken = comboToken;
    this.isBanned = isBanned;
  }

  public int getUid() {
    return uid;
  }

  public String getUsername() {
    return username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getDispatchToken() {
    return dispatchToken;
  }

  public String getComboToken() {
    return comboToken;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public static String getTableQuery() {
    return """
            CREATE TABLE IF NOT EXISTS accounts (
                uid INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                dispatch_token TEXT UNIQUE NOT NULL,
                combo_token TEXT UNIQUE NOT NULL,
                is_banned BOOLEAN NOT NULL DEFAULT 0
            )
        """;
  }

  public static class DAO {
    private static Account map(Map<String, Object> row) {
      return new Account(
          ((Number) row.get("uid")).intValue(),
          (String) row.get("username"),
          (String) row.get("password_hash"),
          (String) row.get("dispatch_token"),
          (String) row.get("combo_token"),
          ((Number) row.get("is_banned")).intValue() != 0);
    }

    public static Account getByUid(int uid) throws SQLException {
      List<Map<String, Object>> rows =
          Database.fetch("SELECT * FROM accounts WHERE uid = ? LIMIT 1", uid);
      return rows.isEmpty() ? null : map(rows.get(0));
    }

    public static Account getByUsername(String username) throws SQLException {
      List<Map<String, Object>> rows =
          Database.fetch("SELECT * FROM accounts WHERE username = ? LIMIT 1", username);
      return rows.isEmpty() ? null : map(rows.get(0));
    }

    public static Account getByDispatchToken(String dispatchToken) throws SQLException {
      List<Map<String, Object>> rows =
          Database.fetch("SELECT * FROM accounts WHERE dispatch_token = ? LIMIT 1", dispatchToken);
      return rows.isEmpty() ? null : map(rows.get(0));
    }

    public static Account getByComboToken(String comboToken) throws SQLException {
      List<Map<String, Object>> rows =
          Database.fetch("SELECT * FROM accounts WHERE combo_token = ? LIMIT 1", comboToken);
      return rows.isEmpty() ? null : map(rows.get(0));
    }

    public static Account createAccount(
        String username, String passwordHash, String dispatchToken, String comboToken)
        throws SQLException {
      Database.exec(
          "INSERT INTO accounts (username, password_hash, dispatch_token, combo_token, is_banned) VALUES (?, ?, ?, ?, ?)",
          username,
          passwordHash,
          dispatchToken,
          comboToken,
          false);
      return getByUsername(username);
    }

    public static Account updatePwHashByUid(int uid, String newPasswordHash) throws SQLException {
      Database.exec("UPDATE accounts SET password_hash = ? WHERE uid = ?", newPasswordHash, uid);
      return getByUid(uid);
    }

    public static Account updateDispatchTokenByUid(int uid, String newDispatchToken)
        throws SQLException {
      Database.exec("UPDATE accounts SET dispatch_token = ? WHERE uid = ?", newDispatchToken, uid);
      return getByUid(uid);
    }

    public static Account updateComboTokenByUid(int uid, String newComboToken) throws SQLException {
      Database.exec("UPDATE accounts SET combo_token = ? WHERE uid = ?", newComboToken, uid);
      return getByUid(uid);
    }

    public static Account updateBanStatusByUid(int uid, boolean banned) throws SQLException {
      Database.exec("UPDATE accounts SET is_banned = ? WHERE uid = ?", banned, uid);
      return getByUid(uid);
    }
  }
}

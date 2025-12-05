package yuvlian.endstrogen.database.tables;

import yuvlian.endstrogen.database.Database;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Account {
    private final int uid;
    private final String username;
    private final String passwordHash;
    private final String token;
    private final boolean isBanned;

    private Account(int uid, String username, String passwordHash, String token, boolean isBanned) {
        this.uid = uid;
        this.username = username;
        this.passwordHash = passwordHash;
        this.token = token;
        this.isBanned = isBanned;
    }

    public int getUid() { return uid; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getToken() { return token; }
    public boolean isBanned() { return isBanned; }

    public static String getTableQuery() {
        return """
            CREATE TABLE IF NOT EXISTS accounts (
                uid INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                token TEXT UNIQUE NOT NULL,
                is_banned BOOLEAN NOT NULL DEFAULT 0,
            )
        """;
    }

    public static class DAO {
        private static Account map(Map<String, Object> row) {
            return new Account(
                ((Number) row.get("uid")).intValue(),
                (String) row.get("username"),
                (String) row.get("password_hash"),
                (String) row.get("token"),
                ((Number) row.get("is_banned")).intValue() != 0
            );
        }

        public static Account getByUid(int uid) throws SQLException {
            List<Map<String, Object>> rows = Database.fetch(
                "SELECT * FROM accounts WHERE uid = ? LIMIT 1", uid
            );
            return rows.isEmpty() ? null : map(rows.get(0));
        }

        public static Account getByUsername(String username) throws SQLException {
            List<Map<String, Object>> rows = Database.fetch(
                "SELECT * FROM accounts WHERE username = ? LIMIT 1", username
            );
            return rows.isEmpty() ? null : map(rows.get(0));
        }

        public static Account getByToken(String token) throws SQLException {
            List<Map<String, Object>> rows = Database.fetch(
                "SELECT * FROM accounts WHERE token = ? LIMIT 1", token
            );
            return rows.isEmpty() ? null : map(rows.get(0));
        }

        public static Account createAccount(String username, String passwordHash, String token) throws SQLException {
            Database.exec(
                "INSERT INTO accounts (username, password_hash, token, is_banned) VALUES (?, ?, ?, ?)",
                username, passwordHash, token, false
            );
            return getByUsername(username);
        }

        public static Account updatePwHashByUid(int uid, String newPasswordHash) throws SQLException {
            Database.exec(
                "UPDATE accounts SET password_hash = ? WHERE uid = ?",
                newPasswordHash, uid
            );
            return getByUid(uid);
        }

        public static Account updateTokenByUid(int uid, String newToken) throws SQLException {
            Database.exec(
                "UPDATE accounts SET token = ? WHERE uid = ?",
                newToken, uid
            );
            return getByUid(uid);
        }

        public static Account updateBanStatusByUid(int uid, boolean banned) throws SQLException {
            Database.exec(
                "UPDATE accounts SET is_banned = ? WHERE uid = ?",
                banned, uid
            );
            return getByUid(uid);
        }
    }
}

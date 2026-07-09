package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck check) throws Exception {
        String sql = "INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, check.getUrlId());
            stmt.setInt(2, check.getStatusCode());
            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getTitle());
            stmt.setString(5, check.getDescription());
            stmt.setTimestamp(6, check.getCreatedAt());
            stmt.executeUpdate();

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    check.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws Exception {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var rs = stmt.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (rs.next()) {
                var check = new UrlCheck(
                    rs.getLong("id"),
                    rs.getLong("url_id"),
                    rs.getInt("status_code"),
                    rs.getString("h1"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getTimestamp("created_at")
                );
                result.add(check);
            }
            return result;
        }
    }
}
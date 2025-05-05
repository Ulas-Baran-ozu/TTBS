
package database.dao;

import database.DBConnection;
import model.entities.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public void add(User user) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, birth_date, email, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, user.getFirstName());
            p.setString(2, user.getLastName());
            if (user.getBirthDate() != null) {
                p.setDate(3, new Date(user.getBirthDate().getTime()));
            } else {
                p.setNull(3, Types.DATE);
            }
            p.setString(4, user.getEmail());
            p.setString(5, user.getPassword());
            p.executeUpdate();
        }
    }

    @Override
    public User getById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, userId);
            try (ResultSet rs = p.executeQuery()) {
                if (!rs.next()) return null;
                Date bd = rs.getDate("birth_date");
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        bd
                );
            }
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Date bd = rs.getDate("birth_date");
                result.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        bd
                ));
            }
        }
        return result;
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET first_name=?, last_name=?, birth_date=?, email=?, password=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, user.getFirstName());
            p.setString(2, user.getLastName());
            if (user.getBirthDate() != null) {
                p.setDate(3, new Date(user.getBirthDate().getTime()));
            } else {
                p.setNull(3, Types.DATE);
            }
            p.setString(4, user.getEmail());
            p.setString(5, user.getPassword());
            p.setInt(6, user.getUserId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, userId);
            p.executeUpdate();
        }
    }
}

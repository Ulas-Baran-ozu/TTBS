package database.dao;

import model.entities.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void add(User user) throws SQLException;
    User getById(int userId) throws SQLException;
    List<User> getAll() throws SQLException;
    void update(User user) throws SQLException;
    void delete(int userId) throws SQLException;
    User getByEmailAndPassword(String email, String password) throws SQLException;
}

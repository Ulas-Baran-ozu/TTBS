package database.dao;

import model.entities.Route;
import java.sql.SQLException;
import java.util.List;

public interface RouteDAO {
    void add(Route route) throws SQLException;
    Route getById(int routeId) throws SQLException;
    List<Route> getAll() throws SQLException;
    void update(Route route) throws SQLException;
    void delete(int routeId) throws SQLException;
    List<Route> search(String departure, String arrival, java.sql.Date date) throws SQLException;
}

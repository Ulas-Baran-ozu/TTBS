package database.dao;

import database.DBConnection;
import model.entities.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAOImpl implements RouteDAO {
    @Override
    public void add(Route route) throws SQLException {
        String sql = "INSERT INTO routes (train_name, source_station, destination_station, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, route.getTrainName());
            p.setString(2, route.getSourceStation());
            p.setString(3, route.getDestinationStation());
            p.setTimestamp(4, route.getDepartureTime());
            p.setTimestamp(5, route.getArrivalTime());
            p.executeUpdate();
        }
    }

    @Override
    public Route getById(int routeId) throws SQLException {
        String sql = "SELECT * FROM routes WHERE route_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, routeId);
            try (ResultSet rs = p.executeQuery()) {
                if (!rs.next()) return null;
                Timestamp dt = rs.getTimestamp("departure_time");
                Timestamp at = rs.getTimestamp("arrival_time");
                return new Route(
                        rs.getInt("route_id"),
                        rs.getString("train_name"),
                        rs.getString("source_station"),
                        rs.getString("destination_station"),
                        dt,
                        at
                );
            }
        }
    }

    @Override
    public List<Route> getAll() throws SQLException {
        String sql = "SELECT * FROM routes";
        List<Route> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp dt = rs.getTimestamp("departure_time");
                Timestamp at = rs.getTimestamp("arrival_time");
                list.add(new Route(
                        rs.getInt("route_id"),
                        rs.getString("train_name"),
                        rs.getString("source_station"),
                        rs.getString("destination_station"),
                        dt,
                        at
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Route route) throws SQLException {
        String sql = "UPDATE routes SET train_name=?, source_station=?, destination_station=?, departure_time=?, arrival_time=? WHERE route_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, route.getTrainName());
            p.setString(2, route.getSourceStation());
            p.setString(3, route.getDestinationStation());
            p.setTimestamp(4, route.getDepartureTime());
            p.setTimestamp(5, route.getArrivalTime());
            p.setInt(6, route.getRouteId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(int routeId) throws SQLException {
        String sql = "DELETE FROM routes WHERE route_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, routeId);
            p.executeUpdate();
        }
    }
}

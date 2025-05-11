package database.dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteSeatDAOImpl implements RouteSeatDAO {

    @Override
    public List<Integer> getOccupiedSeatIdsByRouteId(int routeId) throws SQLException {
        List<Integer> seatIds = new ArrayList<>();
        String sql = "SELECT seat_id FROM route_seats WHERE route_id = ? AND is_available = 0";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, routeId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    seatIds.add(rs.getInt("seat_id"));
                }
            }
        }
        return seatIds;
    }
    @Override
    public void markSeatAsUnavailable(int routeId, int seatId) throws SQLException {
        String sql = "UPDATE route_seats SET is_available = 0 WHERE route_id = ? AND seat_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, routeId);
            p.setInt(2, seatId);
            p.executeUpdate();
        }
    }
    @Override
    public boolean exists(int routeId, int seatId) throws SQLException {
        String sql = "SELECT 1 FROM route_seats WHERE route_id = ? AND seat_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, routeId);
            p.setInt(2, seatId);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next();
            }
        }
    }
}
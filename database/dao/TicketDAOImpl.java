
package database.dao;

import database.DBConnection;
import model.entities.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {
    @Override
    public void add(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (user_id, route_id, seat_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, ticket.getUserId());
            p.setInt(2, ticket.getRouteId());
            p.setInt(3, ticket.getSeatId());
            p.executeUpdate();
        }
    }

    @Override
    public Ticket getById(int ticketId) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, ticketId);
            try (ResultSet rs = p.executeQuery()) {
                if (!rs.next()) return null;
                Timestamp bt = rs.getTimestamp("booking_time");
                return new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getInt("route_id"),
                        rs.getInt("seat_id"),
                        bt
                );
            }
        }
    }

    @Override
    public List<Ticket> getByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        List<Ticket> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, userId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Timestamp bt = rs.getTimestamp("booking_time");
                    list.add(new Ticket(
                            rs.getInt("ticket_id"),
                            rs.getInt("user_id"),
                            rs.getInt("route_id"),
                            rs.getInt("seat_id"),
                            bt
                    ));
                }
            }
        }
        return list;
    }
    @Override
    public List<Ticket> getDetailedTicketsByUserId(int userId) throws SQLException {
        String sql = """
        SELECT t.ticket_id, t.route_id, t.seat_id, r.train_name, r.departure_time, 
               r.source_station, r.destination_station, s.seat_number
        FROM tickets t
        JOIN routes r ON t.route_id = r.route_id
        JOIN seats s ON t.seat_id = s.seat_id
        WHERE t.user_id = ?
    """;

        List<Ticket> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, userId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket(
                            rs.getInt("ticket_id"),
                            userId,
                            rs.getInt("route_id"),
                            rs.getInt("seat_id"),
                            rs.getTimestamp("departure_time")
                    );
                    t.setTrainName(rs.getString("train_name"));
                    t.setFrom(rs.getString("source_station"));
                    t.setTo(rs.getString("destination_station"));
                    t.setSeatNumber(rs.getString("seat_number"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    @Override
    public List<Ticket> getAll() throws SQLException {
        String sql = "SELECT * FROM tickets";
        List<Ticket> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp bt = rs.getTimestamp("booking_time");
                list.add(new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getInt("route_id"),
                        rs.getInt("seat_id"),
                        bt
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Ticket ticket) throws SQLException {
        String sql = "UPDATE tickets SET user_id=?, route_id=?, seat_id=? WHERE ticket_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, ticket.getUserId());
            p.setInt(2, ticket.getRouteId());
            p.setInt(3, ticket.getSeatId());
            p.setInt(4, ticket.getTicketId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(int ticketId) throws SQLException {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, ticketId);
            p.executeUpdate();
        }
    }
}

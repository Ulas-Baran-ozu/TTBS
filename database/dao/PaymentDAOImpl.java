
package database.dao;

import database.DBConnection;
import model.entities.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public void add(Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (ticket_id, amount, payment_method, payment_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, payment.getTicketId());
            p.setDouble(2, payment.getAmount());
            p.setString(3, payment.getPaymentMethod());
            p.setString(4, payment.getPaymentStatus());
            p.executeUpdate();
        }
    }

    @Override
    public Payment getByTicketId(int ticketId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE ticket_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, ticketId);
            try (ResultSet rs = p.executeQuery()) {
                if (!rs.next()) return null;
                Timestamp pt = rs.getTimestamp("payment_time");
                return new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("ticket_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        pt
                );
            }
        }
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payments";
        List<Payment> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp pt = rs.getTimestamp("payment_time");
                list.add(new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("ticket_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        pt
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Payment payment) throws SQLException {
        String sql = "UPDATE payments SET amount=?, payment_method=?, payment_status=? WHERE payment_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setDouble(1, payment.getAmount());
            p.setString(2, payment.getPaymentMethod());
            p.setString(3, payment.getPaymentStatus());
            p.setInt(4, payment.getPaymentId());
            p.executeUpdate();
        }
    }

    @Override
    public void delete(int paymentId) throws SQLException {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, paymentId);
            p.executeUpdate();
        }
    }
}

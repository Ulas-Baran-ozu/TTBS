package database.dao;

import model.entities.Payment;
import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {
    void add(Payment payment) throws SQLException;
    Payment getByTicketId(int ticketId) throws SQLException;
    List<Payment> getAll() throws SQLException;
    void update(Payment payment) throws SQLException;
    void delete(int paymentId) throws SQLException;
}

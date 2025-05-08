package database.dao;

import model.entities.Ticket;
import java.sql.SQLException;
import java.util.List;

public interface TicketDAO {
    void add(Ticket ticket) throws SQLException;
    Ticket getById(int ticketId) throws SQLException;
    List<Ticket> getByUserId(int userId) throws SQLException;  // ekstra
    List<Ticket> getAll() throws SQLException;
    void update(Ticket ticket) throws SQLException;
    void delete(int ticketId) throws SQLException;
}

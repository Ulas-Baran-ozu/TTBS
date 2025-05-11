package database.dao;

import java.sql.SQLException;
import java.util.List;

public interface RouteSeatDAO {
    List<Integer> getOccupiedSeatIdsByRouteId(int routeId) throws SQLException;
    void markSeatAsUnavailable(int routeId, int seatId) throws SQLException;
    boolean exists(int routeId, int seatId) throws SQLException; // ✅ EKLENDİ
}
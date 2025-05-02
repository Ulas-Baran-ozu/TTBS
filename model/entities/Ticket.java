package model.entities;

import java.sql.Timestamp;

public class Ticket {
    private int ticketId;
    private int userId;
    private int routeId;
    private int seatId;
    private java.sql.Timestamp bookingTime;

    public Ticket(int ticketId, int userId, int routeId, int seatId, Timestamp bookingTime) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.routeId = routeId;
        this.seatId = seatId;
        this.bookingTime = bookingTime;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }
}

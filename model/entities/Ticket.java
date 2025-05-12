package model.entities;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Ticket {
    private int ticketId;
    private int userId;
    private int routeId;
    private int seatId;
    private java.sql.Timestamp bookingTime;
    private String trainName;
    private String from;
    private String to;
    private String seatNumber;

    public Ticket(int userId, int routeId, int seatId) {
        this.userId = userId;
        this.routeId = routeId;
        this.seatId = seatId;
    }

    public Ticket(int ticketId, int userId, int routeId, int seatId, Timestamp bookingTime) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.routeId = routeId;
        this.seatId = seatId;
        this.bookingTime = bookingTime;
    }


    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public LocalDate getDepartureDate() {
        return bookingTime != null ? bookingTime.toLocalDateTime().toLocalDate() : null;
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

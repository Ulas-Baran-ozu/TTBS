package model;

public class TrainJourney {
    private String trainName;
    private int availableSeats;
    private int totalSeats;
    private String departureTime;
    private String duration;
    private String route;

    public TrainJourney(String trainName, int availableSeats, int totalSeats, String departureTime, String duration, String route) {
        this.trainName = trainName;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.departureTime = departureTime;
        this.duration = duration;
        this.route = route;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getRoute() {
        return route;
    }
}
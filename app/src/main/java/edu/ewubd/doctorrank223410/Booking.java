package edu.ewubd.doctorrank223410;

public class Booking {
    public String userId;
    public String doctorId;
    public String doctorName;
    public String specialization;
    public int roomNo;

    public String date, time;

    public String bookingKey;
    public Booking() {}

    public Booking(String userId,String bookingKey) {
        this.userId = userId;
        this.bookingKey = bookingKey;
    }
}


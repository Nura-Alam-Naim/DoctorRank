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

    public Booking(String userId, String doctorId, String doctorName, String specialization,
                   int roomNo, String date, String time, String bookingKey) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.roomNo = roomNo;
        this.date = date;
        this.time = time;
        this.bookingKey = bookingKey;
    }
}


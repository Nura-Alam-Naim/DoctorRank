package edu.ewubd.doctorrank223410;

public class UserBooking {
    public String date, time, doctorName, specialization, doctorId, bookingKey;

    // Default constructor for Firebase
    public UserBooking() {}

    // Constructor with doctorId and bookingKey to make it more complete
    public UserBooking(String date, String time, String doctorName, String specialization, String doctorId, String bookingKey) {
        this.date = date;
        this.time = time;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.doctorId = doctorId;  // Storing doctorId
        this.bookingKey = bookingKey;  // Storing bookingKey
    }
}

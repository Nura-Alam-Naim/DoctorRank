package edu.ewubd.doctorrank223410;

public class UserBooking {
    public String date, time,doctorName,specialization;
    public UserBooking() {}
    public UserBooking(String date, String time, String doctorName, String specialization) {
        this.date = date;
        this.time = time;
        this.doctorName = doctorName;
        this.specialization = specialization;
    }
}

package edu.ewubd.doctorrank223410;

import java.util.List;
import java.util.Map;

public class T_DoctorInfo {
    public String id;
    public String name;
    public String speciality;
    public float rating;
    public int roomNo;
    public String picture;
    public int charge;
    public String BDMC;

    // ✅ New flexible schedule: Map<Day, List<slots>>
    public Map<String, List<String>> schedule;

    // REQUIRED by Firebase (empty constructor)
    public T_DoctorInfo() {}

    // Optional constructor
    public T_DoctorInfo(String id, String name, String speciality, float rating, int roomNo,
                        String picture, int charge, String BDMC,
                        Map<String, List<String>> schedule) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.rating = rating;
        this.roomNo = roomNo;
        this.picture = picture;
        this.charge = charge;
        this.BDMC = BDMC;
        this.schedule = schedule;
    }
}

package edu.ewubd.doctorrank223410;

import androidx.annotation.NonNull;

public class T_DoctorInfo {
    public String name;
    public String speciality;
    public float rating;
    public int roomNo;
    public T_DoctorInfo(String name, String speciality, float rating, int roomNo) {
        this.name=name;
        this.speciality=speciality;
        this.rating=rating;
        this.roomNo=roomNo;
    }
    @NonNull
    public String toString(){
        return name+";"+speciality+";"+rating+";"+roomNo;
    }
}

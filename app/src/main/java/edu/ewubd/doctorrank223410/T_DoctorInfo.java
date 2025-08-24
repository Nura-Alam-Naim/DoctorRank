package edu.ewubd.doctorrank223410;

import androidx.annotation.NonNull;

public class T_DoctorInfo {
    public String name;
    public String speciality;
    public float rating;
    public int roomNo;
    public String picture;
    public T_DoctorInfo(String name, String speciality, float rating, int roomNo, String picture) {
        this.name=name;
        this.speciality=speciality;
        this.rating=rating;
        this.roomNo=roomNo;
        this.picture=picture;
    }
    @NonNull
    public String toString(){
        return name+";"+speciality+";"+rating+";"+roomNo+";"+picture;
    }
}

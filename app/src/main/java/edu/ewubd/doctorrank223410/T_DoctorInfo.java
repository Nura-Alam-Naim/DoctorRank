package edu.ewubd.doctorrank223410;

import androidx.annotation.NonNull;

public class T_DoctorInfo {
    public String name;
    public String speciality;
    public float rating;
    public int roomNo;
    public String picture;
    public int charge;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public T_DoctorInfo(String name, String speciality, float rating, int roomNo, String picture, int charge) {
        this.name=name;
        this.speciality=speciality;
        this.rating=rating;
        this.roomNo=roomNo;
        this.picture=picture;
        this.charge=charge;
    }

    @NonNull
    public String toString(){
        return name+";"+speciality+";"+rating+";"+roomNo+";"+picture;
    }
}

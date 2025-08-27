package edu.ewubd.doctorrank223410;

import androidx.annotation.NonNull;

public class T_DoctorInfo {
    public String id;
    public String name;
    public String speciality;
    public float rating;
    public int roomNo;
    public String picture;
    public int charge;
    public String BDMC;

    public String slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8;

    // REQUIRED by Firebase (no-arg)
    public T_DoctorInfo() {}

    public T_DoctorInfo(String id, String name, String speciality, float rating, int roomNo, String picture,
                        int charge, String BDMC, String slot1, String slot2, String slot3, String slot4,
                        String slot5, String slot6, String slot7, String slot8) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.rating = rating;
        this.roomNo = roomNo;
        this.picture = picture;
        this.charge = charge;
        this.BDMC = BDMC;
        this.slot1 = slot1; this.slot2 = slot2; this.slot3 = slot3; this.slot4 = slot4;
        this.slot5 = slot5; this.slot6 = slot6; this.slot7 = slot7; this.slot8 = slot8;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getBDMC() {
        return BDMC;
    }

    public void setBDMC(String BDMC) {
        this.BDMC = BDMC;
    }

    public String getSlot1() {
        return slot1;
    }

    public void setSlot1(String slot1) {
        this.slot1 = slot1;
    }

    public String getSlot2() {
        return slot2;
    }

    public void setSlot2(String slot2) {
        this.slot2 = slot2;
    }

    public String getSlot3() {
        return slot3;
    }

    public void setSlot3(String slot3) {
        this.slot3 = slot3;
    }

    public String getSlot4() {
        return slot4;
    }

    public void setSlot4(String slot4) {
        this.slot4 = slot4;
    }

    public String getSlot5() {
        return slot5;
    }

    public void setSlot5(String slot5) {
        this.slot5 = slot5;
    }

    public String getSlot6() {
        return slot6;
    }

    public void setSlot6(String slot6) {
        this.slot6 = slot6;
    }

    public String getSlot7() {
        return slot7;
    }

    public void setSlot7(String slot7) {
        this.slot7 = slot7;
    }

    public String getSlot8() {
        return slot8;
    }

    public void setSlot8(String slot8) {
        this.slot8 = slot8;
    }
}

package edu.ewubd.doctorrank223410;

public class T_Appointments {
    public String date,name,speciality, time;
    public float rating;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public T_Appointments(String date, String time, String name, String speciality, float rating){
        this.date=date;
        this.name=name;
        this.speciality=speciality;
        this.rating=rating;
        this.time=time;
    }
    public String toString(){
        return date+";"+time+";"+name+";"+speciality+";"+rating;
    }
}

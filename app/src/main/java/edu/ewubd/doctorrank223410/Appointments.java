package edu.ewubd.doctorrank223410;

public class Appointments {
    public String date,name,speciality;
    public float rating;
    public Appointments(String date,String name,String speciality,float rating){
        this.date=date;
        this.name=name;
        this.speciality=speciality;
        this.rating=rating;
    }
    public String toString(){
        return date+";"+name+";"+speciality+";"+rating;
    }
}

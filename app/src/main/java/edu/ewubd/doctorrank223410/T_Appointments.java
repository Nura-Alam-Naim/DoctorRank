package edu.ewubd.doctorrank223410;

public class T_Appointments {
    public String date,name,speciality, time;
    public float rating;
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

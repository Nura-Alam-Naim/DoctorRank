package edu.ewubd.doctorrank223410;

public class T_Users {
    public String name, email,phone,password, gender, dob, height, weight,  image, UID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
    public T_Users() {}
    public T_Users(String name, String email, String phone, String password, String gender, String dob, String height, String weight, String  image, String UID)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.image = image;
        this.UID = UID;
    }

}

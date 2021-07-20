package com.homeandlearn.ken.navigationframe;

public class UserData
{
    public String first_name;
    public String last_name;
    public String email;
    public String mobile_no;
    public String bloodgroup;
    public String type;

    public UserData()
    {

    }
    public UserData(String first_name, String last_name, String email, String mobile_no, String bloodgroup, String type) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.bloodgroup = bloodgroup;
        this.type = type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

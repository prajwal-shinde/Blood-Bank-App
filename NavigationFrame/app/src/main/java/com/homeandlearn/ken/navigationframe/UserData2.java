package com.homeandlearn.ken.navigationframe;

public class UserData2
{
    public String type1;
    public String blood_group;
    public String no_units;
    public String condition;
    public String firstname;
    public String lastname;

    public UserData2()
    {

    }

    public UserData2(String type1, String blood_group, String no_units, String condition, String firstname, String lastname) {
        this.type1 = type1;
        this.blood_group = blood_group;
        this.no_units = no_units;

        this.condition = condition;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public String getType1()
    {
        return type1;
    }

    public void setType1(String type1)
    {
        this.type1 = type1;
    }

    public String getBlood_group()
    {
        return blood_group;
    }

    public void setBlood_group(String blood_group)
    {
        this.blood_group = blood_group;
    }

    public String getNo_units() {
        return no_units;
    }

    public void setNo_units(String no_units)
    {
        this.no_units = no_units;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
}
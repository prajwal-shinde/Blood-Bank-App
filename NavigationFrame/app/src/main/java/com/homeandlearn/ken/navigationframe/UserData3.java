package com.homeandlearn.ken.navigationframe;

public class UserData3
{
    public String bld_grp;
    public String dist;
    public String cust_name;
    public String mob_num;

    public UserData3() {
    }

    public UserData3(String bld_grp, String dist, String cust_name, String mob_num) {
        this.bld_grp = bld_grp;
        this.dist = dist;
        this.cust_name = cust_name;
        this.mob_num = mob_num;
    }

    public String getBld_grp() {
        return bld_grp;
    }

    public void setBld_grp(String bld_grp) {
        this.bld_grp = bld_grp;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getMob_num() {
        return mob_num;
    }

    public void setMob_num(String mob_num) {
        this.mob_num = mob_num;
    }
}
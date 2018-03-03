package joslabs.companyx;

/**
 * Created by OSCAR on 8/24/2017.
 */

public class users {
    String fname,lname,phone,lats,longs,time,salespersonKey,spkey;

    public users() {
    }

    public users(String fname, String lname, String phone, String lats, String longs,String time, String salespersonKey,String spkey) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.lats = lats;
        this.longs=longs;
        this.time = time;
        this.salespersonKey = salespersonKey;
        this.spkey=spkey;
    }

    public String getFname() {
        return fname;
    }

    public String getSpkey() {
        return spkey;
    }

    public void setSpkey(String spkey) {
        this.spkey = spkey;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLats() {
        return lats;
    }

    public void setLats(String lats) {
        this.lats = lats;
    }

    public String getLongs() {
        return longs;
    }

    public void setLongs(String longs) {
        this.longs = longs;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSalespersonKey() {
        return salespersonKey;
    }

    public void setSalespersonKey(String salespersonKey) {
        this.salespersonKey = salespersonKey;
    }
}

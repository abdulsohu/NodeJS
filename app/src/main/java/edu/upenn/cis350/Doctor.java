package edu.upenn.cis350;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Doctor implements Serializable {
    String username;
    String password;
    String email;
    String telephone;
    String speciality;
    String hourly_rate;
    public Doctor(String username, String password, String email, String telephone,
                       String speciality, String hourly_rate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.speciality = speciality;
        this.hourly_rate = hourly_rate;
    }
}



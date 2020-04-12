package edu.upenn.cis350;

import java.io.Serializable;

public class Appointment implements Serializable {
    String dUsername;
    String pUsername;
    String status;
    String pTelephone;
    String need;
    String address;
    String dateTime;


    public Appointment(String dUsername, String pUsername, String status, String pTelephone,
                       String need, String address, String dateTime) {
        this.dUsername = dUsername;
        this.pUsername = pUsername;
        this.status = status;
        this.pTelephone = pTelephone;
        this.need = need;
        this.address = address;
        this.dateTime = dateTime;
    }
}

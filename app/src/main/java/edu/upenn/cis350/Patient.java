package edu.upenn.cis350;

import java.io.Serializable;

public class Patient implements Serializable {
    String username;
    String password;
    String email;
    String telephone;
    String fullname;
    public Patient(String username, String password, String email, String telephone,
                  String fullname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.fullname = fullname;
    }
}

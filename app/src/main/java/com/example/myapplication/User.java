package com.example.myapplication;

import java.util.Vector;

public class User {
    public static String defaultEmail = "default";
    public static String defaultPassword = "default";

    private String email;
    private String password;
    private Vector<Object> v;

    public User(){
        email = defaultEmail;
        password = defaultPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vector<Object> getV() {
        return v;
    }

    public void setV(Vector<Object> v) {
        this.v = v;
    }
}

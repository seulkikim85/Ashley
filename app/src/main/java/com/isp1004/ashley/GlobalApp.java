package com.isp1004.ashley;

import android.app.Application;

/**
 * Created by SEULKI on 2017-03-16.
 */

public class GlobalApp extends Application {

    //private String serverUri = "http://10.0.9.239";

    private String email;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
/*
    public String getServerUri() {
        return serverUri;
    }
    */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.umawallet.api.responsepojos;

import java.io.Serializable;

/**
 * Created by chintans on 21-02-2018.
 */
public class LoginRequest implements Serializable {
    private String Email;
    private String Password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

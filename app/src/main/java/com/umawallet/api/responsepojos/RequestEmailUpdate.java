package com.umawallet.api.responsepojos;

/**
 * Created by Shriji on 3/31/2018.
 */

public class RequestEmailUpdate {
private String UserId;
private String Email;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

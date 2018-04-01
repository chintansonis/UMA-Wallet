package com.umawallet.api.responsepojos;

/**
 * Created by Shriji on 3/31/2018.
 */

public class RequeslUpdateProfile {
    private String UserId;
    private String FullName;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}

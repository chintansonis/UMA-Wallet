package com.umawallet.api.responsepojos;

import java.io.Serializable;

/**
 * Created by chintans on 22-02-2018.
 */

public class RequestPassword implements Serializable {

    private String UserId;
    private String OldPassword;
    private String NewPassword;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }
}

package com.umawallet.api;

import com.umawallet.api.responsepojos.LoginRequest;
import com.umawallet.api.responsepojos.LoginResponse;
import com.umawallet.api.responsepojos.RequestFromAddress;
import com.umawallet.api.responsepojos.RequestPassword;
import com.umawallet.api.responsepojos.RequestRegister;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.api.responsepojos.ResponseGetWallet;
import com.umawallet.api.responsepojos.ResponseTransaction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by chintans on 21-02-2018.
 */

public interface AppApi {

    @GET("forgotPassword.php?Email=")
    Call<BaseResponse> forgotPasswordApi(@Query("Email") String email);

    @GET("getUserAddress.php?UserId=")
    Call<ResponseGeUserAddress> getUserAddressApi(@Query("UserId") String userId);

    @POST("userLogin.php")
    Call<LoginResponse> loginApi(@Body LoginRequest loginRequest);

    @POST("getTranscation.php")
    Call<ResponseTransaction> getTranscationApi(@Body RequestFromAddress requestFromAddress);

    @POST("Signup.php")
    Call<LoginResponse> RegisterApi(@Body RequestRegister requestRegister);

    @GET("getAllWallets.php")
    Call<ResponseGetWallet> getWalletApi();

    @POST("updatePassword.php")
    Call<BaseResponse> updatePasswordApi(@Body RequestPassword requestPassword);



    /*
    @POST("userProfile.php")
    Call<LoginResponse> getProfile(@Body RequestUserID requestUserID);



    @POST("SignupStep1.php")
    Call<BaseResponse> signUpStepOneApi(@Body SignUpStepOneRequest signUpStepOneRequest);

    @POST("SaveEmergencyContact.php")
    Call<BaseResponse> saveEmergencyConatactApi(@Body SaveEmergencyContactRequest contactJson);

    @POST("updateMyInterest.php")
    Call<LoginResponse> updateMyInterestApi(@Body RequestUpdateInterest requestUpdateInterest);
*/

}

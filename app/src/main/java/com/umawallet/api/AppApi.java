package com.umawallet.api;

import com.umawallet.api.responsepojos.LoginRequest;
import com.umawallet.api.responsepojos.LoginResponse;
import com.umawallet.api.responsepojos.RequeslUpdateProfile;
import com.umawallet.api.responsepojos.RequestEmailUpdate;
import com.umawallet.api.responsepojos.RequestFromAddress;
import com.umawallet.api.responsepojos.RequestPassword;
import com.umawallet.api.responsepojos.RequestRegister;
import com.umawallet.api.responsepojos.ResponseGeUserAddress;
import com.umawallet.api.responsepojos.ResponseGetPrice;
import com.umawallet.api.responsepojos.ResponseGetWallet;
import com.umawallet.api.responsepojos.ResponseGraphData;
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

    @GET("deleteAddressWallet.php?AddressId=")
    Call<BaseResponse> deleteWalletApi(@Query("AddressId") String email);

    @GET("getUserAddress.php?UserIdCur=")
    Call<ResponseGeUserAddress> getUserAddressApi(@Query("UserId") String userId, @Query("Cur") String cur);

    @GET("getEduStarClassicGraph.php?CurType=")
    Call<ResponseGraphData> getEduClassicGraphDatasApi(@Query("Cur") String cur, @Query("Type") String type);

    @GET("getEduStarLiteGraph.php?CurType=")
    Call<ResponseGraphData> getEduLiteGraphDatasApi(@Query("Cur") String cur, @Query("Type") String type);

    @GET("getPrice.php?CUR=")
    Call<ResponseGetPrice> getPriceApi(@Query("CUR") String cur);

    @POST("userLogin.php")
    Call<LoginResponse> loginApi(@Body LoginRequest loginRequest);

    @POST("addAddressWallet.php")
    Call<BaseResponse> addWalletApi(@Body RequestAddWallet requestAddWallet);

    @POST("Transfer.php")
    Call<BaseResponse> transferApi(@Body RequestTransfer requestTransfer);

    @POST("updateAddressWallet.php")
    Call<BaseResponse> updateWalletApi(@Body RequestUpdateWallet requestUpdateWallet);

    @POST("updateEmail.php")
    Call<BaseResponse> updateEmailApi(@Body RequestEmailUpdate requestEmailUpdate);

    @POST("updateProfile.php")
    Call<BaseResponse> updateProfileApi(@Body RequeslUpdateProfile requeslUpdateProfile);

    @POST("getTranscation.php")
    Call<ResponseTransaction> getTranscationApi(@Body RequestFromAddress requestFromAddress);

    @POST("Signup.php")
    Call<LoginResponse> RegisterApi(@Body RequestRegister requestRegister);

    @GET("getAllWallets.php")
    Call<ResponseGetWallet> getWalletApi();

    @POST("updatePassword.php")
    Call<BaseResponse> updatePasswordApi(@Body RequestPassword requestPassword);

}

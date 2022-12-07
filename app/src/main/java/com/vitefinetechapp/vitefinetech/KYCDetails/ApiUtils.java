package com.vitefinetechapp.vitefinetech.KYCDetails;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    private static ApiInterface api;

    public ApiInterface getService(){
        return api;
    }
    public static final String BASE_URL = "https://vitefintech.com/viteapi/kyc/";

    public ApiUtils(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiInterface.class);
    }
}

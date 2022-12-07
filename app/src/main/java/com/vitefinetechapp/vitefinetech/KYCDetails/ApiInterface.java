package com.vitefinetechapp.vitefinetech.KYCDetails;

import com.google.gson.JsonObject;
import com.vitefinetechapp.vitefinetech.GST_Registration.GSTModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("file_upload")
    Call<ResponseBody> uploadmage(@Part MultipartBody.Part image,
                                  @Part("user_id")RequestBody userId,
                                  @Part("type") RequestBody type);

    @Headers({"Accept: application/json"})
    @Multipart
    @POST("gstreturn_post")
    Call<ResponseBody> uploadGstRetrunData(@Part MultipartBody.Part image,
                                           @Part("created_by")RequestBody userId,
                                           @Part("type") RequestBody type);


    @Multipart
    @POST("gstreturn_post")
    Call<JsonObject> postImage(@Part("created_by")         RequestBody memberId,
                               @Part("type")               RequestBody actionType,
                               @Part MultipartBody.Part[]  multipartTypedOutput);


    @Multipart
    @POST("index_post")
    Call<JsonObject> postImageNewData(@Part("state")         RequestBody state,
                                      @Part("district")               RequestBody district,
                                      @Part("business_adress")               RequestBody business_adress,
                                      @Part("nob")               RequestBody nob,
                                      @Part("nop")               RequestBody nop,
                                      @Part("firm_name")               RequestBody firm_name,
                                      @Part("com_type")               RequestBody com_type,
                                      @Part("created_by")               RequestBody created_by,

                                      @Part MultipartBody.Part[]  multipartTypedPhoto,
                                      @Part MultipartBody.Part[]  multipartTypedAdharF,
                                      @Part MultipartBody.Part[]  multipartTypedAdharB,
                                      @Part MultipartBody.Part[]  multipartTypedPanFile,
                                      @Part MultipartBody.Part[]  multipartTypedStatemt,
                                      @Part MultipartBody.Part imageElectricity,
                                      @Part MultipartBody.Part imageRentAgrr,
                                      @Part MultipartBody.Part imageCOi,
                                      @Part("name[]") ArrayList<String> name,
                                      @Part("m_name[]") ArrayList<String> m_name,
                                      @Part("dob[]") ArrayList<String> dob,
                                      @Part("email[]") ArrayList<String> email,
                                      @Part("mobile[]") ArrayList<String> mobile,
                                      @Part("pan_no[]") ArrayList<String> pan_no,
                                      @Part("adhar_no[]") ArrayList<String> adhar_no,
                                      @Part("address[]") ArrayList<String> address,
                                      @Part("din[]") ArrayList<String> din


    );



    @Multipart
    @PUT("gst/")
    Call<GSTModel> uploadImageData(@Part MultipartBody.Part auth_sign_file,
                                   @Part MultipartBody.Part moa_file,
                                   @Part MultipartBody.Part aoa_file,
                                   @Part MultipartBody.Part electricity_bill,
                                   @Part MultipartBody.Part rent_agreement,
                                   @Part("state")                  RequestBody email,
                                   @Part("district")               RequestBody mobileNo,
                                   @Part("business_adress")        RequestBody person_name,
                                   @Part("nob")                    RequestBody f_h_name,
                                   @Part("created_by")             RequestBody dob,
                                   @Part("firm_name")              RequestBody designation,
                                   @Part("nop")                    RequestBody pan_no,
                                   @Part("com_type")               RequestBody adhar_no
    );



    @Multipart
    @POST("comreg_post")
    Call<JsonObject> postImageComReg(@Part("state")                RequestBody state,
                                     @Part("district")            RequestBody district,
                                     @Part("business_adress")     RequestBody business_adress,
                                     @Part("nop")                 RequestBody nop,
                                     @Part("firm_name")           RequestBody firm_name,
                                     @Part("created_by")          RequestBody created_by,

                                     @Part MultipartBody.Part[]   multipartTypedPhoto,
                                     @Part MultipartBody.Part[]   multipartTypedAdharF,
                                     @Part MultipartBody.Part[]   multipartTypedAdharB,
                                     @Part MultipartBody.Part[]   multipartTypedPanFile,
                                     @Part MultipartBody.Part[]   multipartTypedStatemt,

                                     @Part MultipartBody.Part     imageElectricity,
                                     @Part MultipartBody.Part     imageRentAgrr,

                                     @Part("name[]")           ArrayList<String> name,
                                     @Part("m_name[]")         ArrayList<String> m_name,
                                     @Part("dob[]")            ArrayList<String> dob,
                                     @Part("email[]")          ArrayList<String> email,
                                     @Part("mobile[]")         ArrayList<String> mobile,
                                     @Part("pan_no[]")         ArrayList<String> pan_no,
                                     @Part("adhar_no[]")       ArrayList<String> adhar_no,
                                     @Part("designation[]")    ArrayList<String> designation,
                                     @Part("address[]")        ArrayList<String> address


    );
}

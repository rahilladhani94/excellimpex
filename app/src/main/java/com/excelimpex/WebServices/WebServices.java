package com.excelimpex.WebServices;



import com.excelimpex.Model.CategoryMain;
import com.excelimpex.Model.DistributorMain;
import com.excelimpex.Model.ExpansesMainResponse;
import com.excelimpex.Model.LoginManager.LoginManager;
import com.excelimpex.Model.LoginUser.Login;
import com.excelimpex.Model.LoginUser.UpdateProfile;
import com.excelimpex.Model.ProductlistMain;
import com.excelimpex.Model.ProductsRequestModel;
import com.excelimpex.Model.SimpleMessageStatusResponse;
import com.excelimpex.Model.TargetListMain;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;


public interface WebServices {



    @FormUrlEncoded
    @POST("/login")
    public  void getLogin(@FieldMap Map<String, String> map,
                          Callback<Login> callback);


    @FormUrlEncoded
    @POST("/login")
    public  void getLoginManager(@FieldMap Map<String, String> map,
                                 Callback<LoginManager> callback);

    @FormUrlEncoded
    @POST("/ProductList")
    public  void getProductlist(@FieldMap Map<String, String> map, Callback<ProductlistMain> callback);


    @GET("/CategoryLists")
    public  void getCategorylist(Callback<CategoryMain> callback);


    @FormUrlEncoded
    @POST("/updateProfile")
    public  void getUpdateProfile(@FieldMap Map<String, String> map,
                                  Callback<UpdateProfile> callback);

    @FormUrlEncoded
    @POST("/ExpanceLists")
    public  void getExpanseslist(@FieldMap Map<String, String> map,
                                 Callback<ExpansesMainResponse> callback);


    @Multipart
    @POST("/expance")
    public void addExpanseFare(@Part("sFareImage") TypedFile file, @PartMap Map<String, String> map, Callback<SimpleMessageStatusResponse> callback);



    @Multipart
    @POST("/expance")
    public void addExpanseFareStXe(@Part("sFareImage") TypedFile filefare, @Part("sStationeryImage") TypedFile file, @PartMap Map<String, String> map, Callback<SimpleMessageStatusResponse> callback);

    @Multipart
    @POST("/storevisit")
    public void addStoreVisit(@Part("sImage") TypedFile file, @PartMap Map<String, String> map, Callback<SimpleMessageStatusResponse> callback);

    @FormUrlEncoded
    @POST("/addordera")
    public void addOrder(@FieldMap Map<String, String> map, Callback<SimpleMessageStatusResponse> callback);

    @FormUrlEncoded
    @POST("/TargetLists")
    public  void getTargetlist(@FieldMap Map<String, String> map,
                               Callback<TargetListMain> callback);

    @FormUrlEncoded
    @POST("/DistributorLists")
    public  void getDistributorlist(@FieldMap Map<String, String> map,
                                    Callback<DistributorMain> callback);

    @FormUrlEncoded
    @POST("/SalesPersonMap")
    public  void sendLocationUpdate(@FieldMap Map<String, String> map,
                                    Callback<SimpleMessageStatusResponse> callback);


    @FormUrlEncoded
    @POST("/Signin")
    public  void signinWork(@FieldMap Map<String, String> map,
                                    Callback<SimpleMessageStatusResponse> callback);
}








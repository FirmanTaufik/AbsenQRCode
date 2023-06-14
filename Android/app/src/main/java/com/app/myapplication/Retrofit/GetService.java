package com.app.myapplication.Retrofit;




import com.app.myapplication.Model.Home;
import com.app.myapplication.Model.Post;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetService {



    @POST("login.php")
    @FormUrlEncoded
    Call<Post> postLogin(@FieldMap HashMap<String, String> params);


    @GET("home.php")
    Call<Home> getHome(@Query("id_dosen") String id_dosen);

}
package com.app.myapplication.Retrofit;


import com.app.myapplication.Model.Absen;
import com.app.myapplication.Model.DetailKelas;
import com.app.myapplication.Model.Home;
import com.app.myapplication.Model.Post;
import com.app.myapplication.Model.Rekap;
import com.app.myapplication.Model.RekapPertemuan;
import com.app.myapplication.Model.SiswaKelas;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
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

    @GET("all_mahasiswa.php")
    Call<String> getAllMahasiswa();


    @GET("kelas.php")
    Call<DetailKelas> getKelas(@Query("id") String id_kelas,
                               @Query("idMk") String idMk);

    @POST("post_presensi.php")
    @FormUrlEncoded
    Call<Post> postAbsen(@Field("Pertemuan") String pertemuan,
                         @Field("Tanggal") String Tanggal,
                         @Field("Id_mk") String Id_mk,
                         @Field("Jsonlist") String jsonlist,
                         @Field("Id_kelas") String Id_kelas,
                         @Field("StatusJson") String status);

    @GET("absen.php")
    Call<List<Absen>> getAbsen(@Query("pertemuan") String pertemuan,
                               @Query("idMk") String idMk);


    @POST("edit_pertemuan.php")
    @FormUrlEncoded
    Call<Post> editAbsen(@Field("Pertemuan") String pertemuan,
                         @Field("Tanggal") String Tanggal,
                         @Field("Jsonlist") String jsonlist);


    @POST("delete_pertemuan.php")
    @FormUrlEncoded
    Call<Post> deletePertemuan(@Field("Pertemuan") String pertemuan,
                               @Field("idMk") String idMk);

    @GET("rekap.php")
    Call<List<Rekap>> getRekap(
            @Query("idMk") String idMk,
            @Query("date1") String date1,
            @Query("date2") String date2,
            @Query("idKelas") String idKelas);



    @GET("rekap_pertemuan.php")
    Call<List<RekapPertemuan>> getRekapPertemuan(
            @Query("idMk") String idMk,
            @Query("idKelas") String idKelas);


    @GET("siswa_kelas.php")
    Call<String> getSiswaKelas( );
}
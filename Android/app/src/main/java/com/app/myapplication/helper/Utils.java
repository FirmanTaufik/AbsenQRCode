package com.app.myapplication.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;

import com.app.myapplication.Model.Mahasiswa;
import com.app.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Utils {
    private static SharedPreferences mySharedPreferences;
    private static String PREF = "pref";
    private static String IDUSER= "idUser";

    public static void setUserId(Context context, String id){
        mySharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = mySharedPreferences.edit();
        myEditor.putString(IDUSER, id);
        myEditor.commit();
    }

    public static String getUserId(Context context){
        mySharedPreferences = context.getSharedPreferences(PREF, 0);
        return mySharedPreferences.getString(IDUSER, null);
        // return "1";
    }



    private static String AllMAHASISWAJSON= "AllMAHASISWAJSON";

    public static void setAllMahasiswaJson(Context context, String id){
        mySharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = mySharedPreferences.edit();
        myEditor.putString(AllMAHASISWAJSON, id);
        myEditor.commit();
    }

    public static String getAllMahasiswaJson(Context context){
        mySharedPreferences = context.getSharedPreferences(PREF, 0);
        return mySharedPreferences.getString(AllMAHASISWAJSON, null);
        // return "1";
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static Mahasiswa getMahasiswa(String id){
        Type listType = new TypeToken<List<Mahasiswa>>() {}.getType();
        ArrayList<Mahasiswa> list = new Gson().fromJson(getAllMahasiswaJson(App.getContext()),listType);
        for (Mahasiswa mahasiswa: list ) {
            if (Objects.equals(id, mahasiswa.getIdMhs())) {
                return mahasiswa;
            }
        }
        return new Mahasiswa();
    }


    public static void dialogDate(Context context, EditText editText){
        Calendar newDate = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog( context, (view1, year, month, dayOfMonth) -> {
            newDate.set(year, month, dayOfMonth);

            editText.setText(changeFromDate(newDate.getTime()));

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static String changeFromDate(Date date){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormater.format(date);
    }

    public static void dialogConfirmation(Context context, SweetAlertDialog.OnSweetClickListener onSweetClickListener){
        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Informasi");
        sweetAlertDialog.setContentText("Anda yakin?");
        sweetAlertDialog.setConfirmText("Iya");
        sweetAlertDialog.setConfirmClickListener(onSweetClickListener);
        sweetAlertDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        sweetAlertDialog.show();
    }

    public static void showDateRange(FragmentManager supportFragmentManager, MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> materialPickerOnPositiveButtonClickListener){
        MaterialDatePicker<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker(). build() ;
        builder.addOnPositiveButtonClickListener(materialPickerOnPositiveButtonClickListener);
        builder.show(supportFragmentManager, "Pilih Tanggal");
    }

    public static String longTOyyyyMMdd(Long lastmodified){
        Date lm = new Date(lastmodified);
        String lasmod = new SimpleDateFormat("yyyy-MM-dd").format(lm);
        return lasmod;
    }

    public static void createWebPrintJob(WebView webView, Activity activity) {
        PrintManager printManager = (PrintManager) activity.getSystemService(Context.PRINT_SERVICE);

        String jobName =activity.getString(R.string.app_name) ;
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.NA_GOVT_LETTER);
        printManager.print(jobName, printAdapter,builder.build());
    }


    public static void loadImage(String url, ImageView imageView){
        Glide.with(App.getContext())
                .load(url).into(imageView);
    }
}

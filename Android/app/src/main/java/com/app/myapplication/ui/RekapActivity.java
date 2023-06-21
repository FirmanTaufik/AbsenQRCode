package com.app.myapplication.ui;

import static com.app.myapplication.helper.Utils.createWebPrintJob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.app.myapplication.Model.Home;
import com.app.myapplication.Model.Rekap;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.ActivityRekapBinding;
import com.app.myapplication.helper.Utils;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapActivity extends AppCompatActivity {
    private String TAG ="RekapActivityTAG";
    private ActivityRekapBinding binding;
    private String idMk = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityRekapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWebPrintJob(binding.webView, RekapActivity.this);
            }
        });
        initUi();
    }

    private void initUi() {
        Type listType = new TypeToken<List<Home.Mengajar>>(){}.getType();
        ArrayList<Home.Mengajar> list = new Gson().fromJson(getIntent().getStringExtra("json"),listType );
        String[] item =   new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, "initUi: "+ list.get(i).getNamaMk() +" "+list.get(i).getNamaKelas());
             item[i] = list.get(i).getNamaMk() +" "+list.get(i).getNamaKelas();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,item);
        binding.edtPertemuan.setAdapter(adapter);
        binding.edtPertemuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  idMk = list.get(position).getIdMk();
                Log.d(TAG, "onItemClick: "+idMk);
            }
        });

        binding.edtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idMk.equals("0")) {
                    Toast.makeText(RekapActivity.this, "Pilih dulu kelas dan Mata Kuliah", Toast.LENGTH_SHORT).show();
                    return;
                }
                Utils.showDateRange(getSupportFragmentManager(),new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        String date1 = Utils.longTOyyyyMMdd(selection.first);
                        String date2 = Utils.longTOyyyyMMdd(selection.second);
                        Log.d(TAG, "onPositiveButtonClick: "+date1 +" "+date2);
                        binding.edtTgl.setText(date1 +" - "+date2);
                        getData(idMk, date1, date2);
                    }
                });
            }
        });

    }

    private void getData(String idMk, String date1, String date2) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.GONE);
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getRekap(idMk, date1, date2)
                .enqueue(new Callback<List<Rekap>>() {
                    @Override
                    public void onResponse(Call<List<Rekap>> call, Response<List<Rekap>> response) {
                        if (response.isSuccessful()){
                            setList(response.body());
                        }
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.button.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Rekap>> call, Throwable t) {
                        binding.progressCircular.setVisibility(View.GONE);
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }

    private void setList(List<Rekap> list) {
        Log.d(TAG, "setList: "+list.size());
        StringBuilder stringBuilder = new StringBuilder();
        String top ="<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head>\n" +
                "  <title>"+ getString(R.string.app_name)+"</title>\n" +
                "  <style type=\"text/css\">\n" +
                "    table,th, td{\n" +
                "        padding: 5px;\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse; }\n" +
                "    table{ width: 100%; }\n" +
                "    th, td{ text-align:center; }\n" +
                "  </style>\n" +
                " </head>\n" +
                "<body>\n" ;
        stringBuilder.append(top);
        String m = "<center> <h3> Rekap Pertemuan  "+binding.edtPertemuan.getText().toString() + "   Tanggal "
                +  binding.edtTgl.getText().toString() + "</center> <h3> \n";
        stringBuilder.append(m);
        String body=  " <table>\n" +
                "        <tr>\n" +
                "            <td>No</td>\n" +
                "            <td  >Tanggal</td>\n" +
                "            <td>NIM</td>\n" +
                "            <td style=\"width:500px\">Nama Mahasiswa</td>\n" +
                "            <td>Jurusan</td>\n" +
                "        </tr>\n";
        stringBuilder.append(body);
        int baris=0;
        for (Rekap  l :list) {
            baris++;
            stringBuilder.append( "<tr>\n" +
                    "            <td>"+baris+"</td>\n" +
                    "            <td  >"+l.getTanggal()+"</td>\n" +
                    "            <td>"+l.getNim()+"</td>\n" +
                    "            <td>"+l.getNama()+"</td>\n" +
                    "            <td>"+l.getJurusan()+"</td>\n" +
                    "        </tr>\n");

        }

        String bot = "" +
                "    </table>\n" +
                "</html>";

        stringBuilder.append(bot);
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        binding.webView.getSettings().setUserAgentString(newUA);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.setWebChromeClient(new WebChromeClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);

        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDisplayZoomControls(false);

        binding.webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webView.setScrollbarFadingEnabled(false);
        binding.webView.loadData(stringBuilder.toString(), "text/HTML", "UTF-8");

    }
}
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
import com.app.myapplication.Model.RekapPertemuan;
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
    private String TAG = "RekapActivityTAG";
    private ActivityRekapBinding binding;
    private String idMk = "0";
    private String idKelas = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRekapBinding.inflate(getLayoutInflater());
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
        if (getIntent().hasExtra("isRekapPertemuan")) binding.inputTgl.setVisibility(View.GONE);
        Type listTypekelas = new TypeToken<List<Home.Kela>>() {
        }.getType();
        ArrayList<Home.Kela> listKelas = new Gson().fromJson(getIntent().getStringExtra("jsonKelas"), listTypekelas);
        String[] itemKelas = new String[listKelas.size()];

        for (int i = 0; i < listKelas.size(); i++) {
            itemKelas[i] = listKelas.get(i).getNamaKelas();
        }
        ArrayAdapter adapterKelas = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemKelas);
        binding.edtKelas.setAdapter(adapterKelas);
        binding.edtKelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (binding.edtPertemuan.getText().length()==0) {
                    Utils.showToast(RekapActivity.this, "Pilih dulu mata kuliah");
                    binding.edtKelas.setText("");
                    return;
                }
                idKelas = listKelas.get(position).getIdKelas();
                if (getIntent().hasExtra("isRekapPertemuan")) getData2(idMk, idKelas);
            }
        });

        Type listType = new TypeToken<List<Home.Mengajar>>() {
        }.getType();
        ArrayList<Home.Mengajar> list = new Gson().fromJson(getIntent().getStringExtra("json"), listType);
        String[] item = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            item[i] = list.get(i).getNamaMk();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item);
        binding.edtPertemuan.setAdapter(adapter);
        binding.edtPertemuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idMk = list.get(position).getIdMk();
            }
        });

        binding.edtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idMk.equals("0")) {
                    Toast.makeText(RekapActivity.this, "Pilih dulu kelas dan Mata Kuliah", Toast.LENGTH_SHORT).show();
                    return;
                }
                Utils.showDateRange(getSupportFragmentManager(), new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        String date1 = Utils.longTOyyyyMMdd(selection.first);
                        String date2 = Utils.longTOyyyyMMdd(selection.second);
                        Log.d(TAG, "onPositiveButtonClick: " + date1 + " " + date2);
                        binding.edtTgl.setText(date1 + " - " + date2);
                        getData(idMk, date1, date2, idKelas);
                    }
                });
            }
        });

    }

    private void getData2(String idMk, String idKelas) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.GONE);
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getRekapPertemuan(idMk, idKelas)
                .enqueue(new Callback<List<RekapPertemuan>>() {
                    @Override
                    public void onResponse(Call<List<RekapPertemuan>> call, Response<List<RekapPertemuan>> response) {
                        if (response.isSuccessful()) {
                            setListPertemuan(response.body());
                        }
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.button.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<RekapPertemuan>> call, Throwable t) {
                        binding.progressCircular.setVisibility(View.GONE);
                        Log.d(TAG, "onFailure: " + t.getMessage());

                    }
                });
    }

    private void getData(String idMk, String date1, String date2, String idKelas) {
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.button.setVisibility(View.GONE);
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getRekap(idMk, date1, date2, idKelas)
                .enqueue(new Callback<List<Rekap>>() {
                    @Override
                    public void onResponse(Call<List<Rekap>> call, Response<List<Rekap>> response) {
                        if (response.isSuccessful()) {
                            setList(response.body());
                        }
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.button.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Rekap>> call, Throwable t) {
                        binding.progressCircular.setVisibility(View.GONE);
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }


    private void setListPertemuan(List<RekapPertemuan> list) {
        Log.d(TAG, "setList: " + list.size());
        StringBuilder stringBuilder = new StringBuilder();
        String top = "<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head>\n" +
                "  <title>" + getString(R.string.app_name) + "</title>\n" +
                "  <style type=\"text/css\">\n" +
                "    table,th, td{\n" +
                "        padding: 5px;\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse; }\n" +
                "    table{ width: 100%; }\n" +
                "    th, td{ text-align:center; }\n" +
                "  </style>\n" +
                " </head>\n" +
                "<body>\n";
        stringBuilder.append(top);
        String m = "<center> <h3> Rekap Pertemuan  " + binding.edtPertemuan.getText().toString() + "   Mata Kuliah  "
                + binding.edtPertemuan.getText().toString() + " Kelas "+binding.edtKelas.getText().toString() +"</center> <h3> \n";
        stringBuilder.append(m);
        String body = "<table border=\"1\" " +
                "<tr>\n" +
                "<th rowspan=\"2\">No</th>\n" +
                "<th rowspan=\"2\">NIM </th>\n" +
                "<th rowspan=\"2\">Nama</th>\n" +
                "<th colspan=\"16\">Pertemuan</th>\n" +
                "</tr>\n";

        stringBuilder.append(body);

        stringBuilder.append("<tr>");
        for (int i = 1; i < 17; i++) {
            stringBuilder.append(" <th>" + i + "</th>");
        }
        stringBuilder.append("</tr>");

        int baris = 1;
        for (int i = 0; i < list.size(); i++) {
            baris = baris+i;
            RekapPertemuan rekapPertemuan = list.get(i);
            stringBuilder.append("<tr>\n" +
                    "<td>" + baris + "</td>\n" +
                    "<td> " + rekapPertemuan.getNim() + "</td>\n" +
                    "<td> " + rekapPertemuan.getNama() + "</td>\n");

            for (Boolean isAda : rekapPertemuan.getPertemuan()) {
                if (isAda) stringBuilder.append("<td>Hadir</td>\n");
                else stringBuilder.append("<td>Tidak Hadir</td>\n");
            }


            stringBuilder.append("\t\t</tr>\n");
        }


        String bot = "</table>\n" +
                "</html>";

        stringBuilder.append(bot);
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";

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

    private void setList(List<Rekap> list) {
        Log.d(TAG, "setList: " + list.size());
        StringBuilder stringBuilder = new StringBuilder();
        String top = "<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head>\n" +
                "  <title>" + getString(R.string.app_name) + "</title>\n" +
                "  <style type=\"text/css\">\n" +
                "    table,th, td{\n" +
                "        padding: 5px;\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse; }\n" +
                "    table{ width: 100%; }\n" +
                "    th, td{ text-align:center; }\n" +
                "  </style>\n" +
                " </head>\n" +
                "<body>\n";
        stringBuilder.append(top);
        String m = "<center> <h3> Rekap Absen Tanggal  " + binding.edtPertemuan.getText().toString() + "   Tanggal "
                + binding.edtTgl.getText().toString() + "</center> <h3> \n";
        stringBuilder.append(m);
        String body = " <table>\n" +
                "        <tr>\n" +
                "            <td>No</td>\n" +
                "            <td  >Tanggal</td>\n" +
                "            <td>NIM</td>\n" +
                "            <td style=\"width:500px\">Nama Mahasiswa</td>\n" +
                "            <td>Jurusan</td>\n" +
                "        </tr>\n";
        stringBuilder.append(body);
        int baris = 0;
        for (Rekap l : list) {
            baris++;
            stringBuilder.append("<tr>\n" +
                    "            <td>" + baris + "</td>\n" +
                    "            <td  >" + l.getTanggal() + "</td>\n" +
                    "            <td>" + l.getNim() + "</td>\n" +
                    "            <td>" + l.getNama() + "</td>\n" +
                    "            <td>" + l.getJurusan() + "</td>\n" +
                    "        </tr>\n");

        }

        String bot = "" +
                "    </table>\n" +
                "</html>";

        stringBuilder.append(bot);
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";

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
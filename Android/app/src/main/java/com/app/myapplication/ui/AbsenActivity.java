package com.app.myapplication.ui;

import static com.app.myapplication.Retrofit.ApiClient.BASE_URL_IMAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.myapplication.Adapter.MahasiswaAdapter;
import com.app.myapplication.Model.Absen;
import com.app.myapplication.Model.Mahasiswa;
import com.app.myapplication.Model.Post;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.ActivityAbsenBinding;
import com.app.myapplication.databinding.DialogMahasiswaBinding;
import com.app.myapplication.helper.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenActivity extends AppCompatActivity {
    private String TAG = "AbsenActivityTAG";
    private ActivityAbsenBinding binding;
    private IntentIntegrator intentIntegrator;
    private MahasiswaAdapter mahasiswaAdapter;
    private ArrayList<Mahasiswa> list = new ArrayList<>();
    private String idKelas ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbsenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        idKelas =getIntent().getStringExtra("idKelas");
        binding.toolbar.setTitle("Pertemuan Ke " + getIntent().getStringExtra("pertemuan"));
        initRv();
        initOnclick();
        if (getIntent().hasExtra("isEdit")) {
            getData();
        }
    }

    private void getData() {
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getAbsen(getIntent().getStringExtra("pertemuan"), getIntent().getStringExtra("idMk"))
                .enqueue(new Callback<List<Absen>>() {
                    @Override
                    public void onResponse(Call<List<Absen>> call, Response<List<Absen>> response) {
                        if (response.isSuccessful()) {
                            for (Absen res : response.body()) {
                                Mahasiswa mahasiswa = Utils.getMahasiswaKelas(res.getIdMhs(),idKelas);
                               /* mahasiswa.setIdMhs(res.getIdMhs());
                                mahasiswa.setNama(res.getNama());
                                mahasiswa.setNim(res.getNim());*/
                                list.add(mahasiswa);
                            }
                            mahasiswaAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Absen>> call, Throwable t) {

                    }
                });
    }

    private void initRv() {
        if (!getIntent().hasExtra("isEdit")) {
            list = Utils.getMahasiswaByKelas(idKelas);
        }

        mahasiswaAdapter = new MahasiswaAdapter(AbsenActivity.this, list);
        binding.recyclerView.setAdapter(mahasiswaAdapter);
        mahasiswaAdapter.setListener(new MahasiswaAdapter.Listener() {
            @Override
            public void ItemClick(int position) {
                Utils.dialogConfirmation(AbsenActivity.this, sDialog -> {
                    sDialog.dismissWithAnimation();
                    list.remove(position);
                    mahasiswaAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void ShowProfile(Mahasiswa data) {
                data = Utils.getMahasiswaKelas(data.getIdMhs(), idKelas);
                DialogMahasiswaBinding view = DialogMahasiswaBinding.inflate(getLayoutInflater());
                AlertDialog.Builder builder = new AlertDialog.Builder(AbsenActivity.this);
                builder.setView(view.getRoot());
                if (data.getFoto() == null || data.getFoto().equals("null"))
                    view.image.setImageResource(R.drawable.ic_person);
                else Utils.loadImage(BASE_URL_IMAGE+data.getFoto(), view.image);

                view.textViewTanggalLahir.setText(data.getTempatTglLhr());
                view.textViewNama.setText(data.getNama());
                view.textViewNim.setText("NIM : "+data.getNim());
                view.textViewJurusan.setText(data.getJurusan());
                final AlertDialog mAlertDialog = builder.create();
                mAlertDialog.show();
            }
        });
        mahasiswaAdapter.notifyDataSetChanged();
    }

    private void initOnclick() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(AbsenActivity.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                initScanner();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Utils.setUserId(AbsenActivity.this, "permintaan tidak di ijinkan");
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        })
                        .check();

            }
        });
    }

    private void initScanner() {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() != null) {
                Log.d(TAG, "onActivityResult: " + Result.getContents());
                Mahasiswa mahasiswa = Utils.getMahasiswaKelas(Result.getContents(), idKelas);
                if (mahasiswa.getIdMhs() != null) {
                    detectReplace(mahasiswa);
                    mahasiswa.setStatus(1);
                    list.add(mahasiswa);
                    Collections.reverse(list);
                    mahasiswaAdapter.notifyDataSetChanged();
                } else  Utils.showToast(AbsenActivity.this, "tidak di temukan, Mahasiswa tersebut tidak terdaftar di kelas ini");

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void detectReplace(Mahasiswa mahasiswa) {
        boolean isFound =false;
        int index = 0;
        for (int i = 0; i <list.size() ; i++) {
            if (Objects.equals(mahasiswa.getIdMhs(), list.get(i).getIdMhs())) {
                index=i;
                isFound =true;
            }
        }
        if (isFound) {
            list.remove(index);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            saveData();
        }
        return false;
    }


    private void saveData() {
        if (list.isEmpty())
            Toast.makeText(this, "daftar mahasiswa hadir kosong", Toast.LENGTH_SHORT).show();
        else {
            String pertemuan = getIntent().getStringExtra("pertemuan");
            String idMk = getIntent().getStringExtra("idMk");
            String tanggal = getIntent().getStringExtra("tanggal");
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) stringBuilder.append(list.get(i).getIdMhs());
                else stringBuilder.append(list.get(i).getIdMhs() + ",");
            }

            StringBuilder stringBuilderStatus = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) stringBuilderStatus.append(list.get(i).getStatus());
                else stringBuilderStatus.append(list.get(i).getStatus() + ",");
            }

            ApiClient.getRetrofitInstance().create(GetService.class)
                    .postAbsen(pertemuan, tanggal, idMk, stringBuilder.toString(),idKelas ,stringBuilderStatus.toString())
                    .enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (response.isSuccessful()) {
                                Utils.showToast(AbsenActivity.this, response.message());
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });


        }
    }
}
package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.myapplication.Adapter.PertemuanAdapter;
import com.app.myapplication.Model.DetailKelas;
import com.app.myapplication.Model.Post;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.ActivityKelasBinding;
import com.app.myapplication.databinding.FormAddEditPertemuanBinding;
import com.app.myapplication.helper.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelasActivity extends AppCompatActivity {
    private String TAG ="KelasActivityTAG";
    private ActivityKelasBinding binding;
    private String idKelas ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityKelasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        idKelas =getIntent().getStringExtra("idKelas");
        initOnClick();
    }

    @Override
    protected void onResume() {
        getData();
        super.onResume();
    }

    private void initOnClick() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddPertemuan();
            }
        });
    }

    private void dialogAddPertemuan() {
        FormAddEditPertemuanBinding view1 = FormAddEditPertemuanBinding.inflate( getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view1.getRoot());
        view1.edtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dialogDate(KelasActivity.this, view1.edtTgl);
            }
        });
        builder.setPositiveButton("Tambah", null);
        builder.setNegativeButton("Cancel", null);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAlertDialog.dismiss();
                        String idMk = getIntent().getStringExtra("idMk");
                        Intent intent = new Intent(KelasActivity.this, AbsenActivity.class);
                        intent.putExtra("pertemuan", view1.edtPertemuan.getText().toString());
                        intent.putExtra("tanggal", view1.edtTgl.getText().toString());
                        intent.putExtra("idMk", idMk);
                        intent.putExtra("idKelas", idKelas);
                        startActivity(intent);
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private void getData() {
        ApiClient.getRetrofitInstance().create(GetService.class)
                .getKelas(getIntent().getStringExtra("idKelas"),getIntent().getStringExtra("idMk"))
                .enqueue(new Callback<DetailKelas>() {
                    @Override
                    public void onResponse(Call<DetailKelas> call, Response<DetailKelas> response) {
                        if (response.isSuccessful()) {
                            DetailKelas.Kelas kelas = response.body().getKelas();
                            binding.txtxKelas.setText(kelas.getNamaKelas());
                            binding.txtSemester.setText(kelas.getTahunAjaran());
                            binding.txtMataKuliah.setText(response.body().getMengajar().getNamaMk());
                            listPertemuan(response.body().getPertemuan());
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailKelas> call, Throwable t) {

                    }
                });
    }

    private void listPertemuan(List<DetailKelas.Pertemuan> pertemuans) {
        PertemuanAdapter adapter= new PertemuanAdapter(KelasActivity.this, pertemuans);
        binding.recyclerView.setAdapter(adapter);
        adapter.setListener(new PertemuanAdapter.Listener() {
            @Override
            public void ItemClick(DetailKelas.Pertemuan pertemuan) {
                Intent intent = new Intent(KelasActivity.this, AbsenActivity.class);
                intent.putExtra("pertemuan", pertemuan.getPertemuan());
                intent.putExtra("tanggal", pertemuan.getTanggal());
                intent.putExtra("idMk", pertemuan.getIdMk());
                intent.putExtra("idKelas", idKelas);
                intent.putExtra("isEdit",true);
                startActivity(intent);
            }

            @Override
            public void onEdit(int position) {
                StringBuilder ids = new StringBuilder();
                for (int i = 0; i < pertemuans.get(position).getAbsen().size(); i++) {
                    if (i == pertemuans.get(position).getAbsen().size() - 1) ids.append(pertemuans.get(position).getAbsen().get(i).getIdPresensi());
                    else ids.append(pertemuans.get(position).getAbsen().get(i).getIdPresensi() + ",");
                }
                String Tanggal = pertemuans.get(position).getTanggal();
                String Pertemuan = pertemuans.get(position).getPertemuan();
                postEdit(ids.toString(),Tanggal, Pertemuan);
            }

            @Override
            public void OnDelete(int position) {
                String idMk = pertemuans.get(position).getIdMk();
                String Pertemuan = pertemuans.get(position).getPertemuan();
                Utils.dialogConfirmation(KelasActivity.this, sDialog -> {
                    sDialog.dismissWithAnimation();
                    ApiClient.getRetrofitInstance().create(GetService.class)
                            .deletePertemuan(Pertemuan, idMk)
                            .enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {
                                    if (response.body().getSucces().equals("1")) {
                                        getData();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable t) {
                                    Log.d(TAG, "onFailure: "+t.getMessage());

                                }
                            });
                });


            }
        });

    }

    private void postEdit(String ids, String tanggal, String pertemuan) {
        FormAddEditPertemuanBinding view1 = FormAddEditPertemuanBinding.inflate( getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view1.getRoot());
        view1.edtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dialogDate(KelasActivity.this, view1.edtTgl);
            }
        });
        view1.edtTgl.setText(tanggal);
        view1.edtPertemuan.setText(pertemuan);
        builder.setPositiveButton("Simpan", null);
        builder.setNegativeButton("Cancel", null);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      ApiClient.getRetrofitInstance().create(GetService.class)
                              .editAbsen( view1.edtPertemuan.getText().toString(),  view1.edtTgl.getText().toString(), ids)
                              .enqueue(new Callback<Post>() {
                                  @Override
                                  public void onResponse(Call<Post> call, Response<Post> response) {
                                      if (response.body().getSucces().equals("1")) {
                                          getData();
                                          mAlertDialog.dismiss();
                                      }
                                  }

                                  @Override
                                  public void onFailure(Call<Post> call, Throwable t) {
                                      Log.d(TAG, "onFailure: "+t.getMessage());
                                  }
                              });
                    }
                });
            }
        });
        mAlertDialog.show();
    }
}
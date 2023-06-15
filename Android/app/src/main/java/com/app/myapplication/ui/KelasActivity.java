package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.myapplication.Adapter.PertemuanAdapter;
import com.app.myapplication.Model.DetailKelas;
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
    private ActivityKelasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityKelasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
    /*    View view1 = LayoutInflater.from(this).inflate(R.layout.form_user,null  );*/
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
                intent.putExtra("isEdit",true);
                startActivity(intent);
            }
        });

    }
}
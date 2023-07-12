package com.app.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.myapplication.Adapter.KelasAdapter;
import com.app.myapplication.Model.Home;
import com.app.myapplication.R;
import com.app.myapplication.databinding.ActivityPilihKelasBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PilihKelasActivity extends AppCompatActivity {
    private String TAG ="PilihKelasActivityTAG";
    private ActivityPilihKelasBinding binding;
    private String idMk = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihKelasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        idMk =getIntent().getStringExtra("idMk");
        Type listType = new TypeToken<List<Home.Kela>>(){}.getType();
        Log.d(TAG, "onCreate: "+getIntent().getStringExtra("json"));
        ArrayList<Home.Kela> list = new Gson().fromJson(getIntent().getStringExtra("json"),listType );
        KelasAdapter kelasAdapter = new KelasAdapter(this, list);
        binding.recyclerView.setAdapter(kelasAdapter);
        kelasAdapter.setCallback(new KelasAdapter.Listener() {
            @Override
            public void setOnClick(int pos) {
                Intent intent = new Intent(PilihKelasActivity.this, KelasActivity.class);
                intent.putExtra("idMk",idMk);
                intent.putExtra("idKelas",list.get(pos).getIdKelas());
                startActivity(intent);
            }
        });
    }
}
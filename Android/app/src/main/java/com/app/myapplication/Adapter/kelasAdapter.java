package com.app.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.myapplication.Model.Home;
import com.app.myapplication.R;
import com.app.myapplication.ui.KelasActivity;

import java.util.ArrayList;
import java.util.List;

public class kelasAdapter extends RecyclerView.Adapter<kelasAdapter.ViewHolder> {
    private Context context;
    private List<Home.Mengajar> listKelas;

    public kelasAdapter(Context context, List<Home.Mengajar> listKelas) {
        this.context = context;
        this.listKelas = listKelas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_kelas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNama.setText(listKelas.get(position).getNamaMk());
        holder.txtSemester.setText(listKelas.get(position).getNamaKelas());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(context, KelasActivity.class);
                intent.putExtra("idKelas",listKelas.get(position).getIdKelas() );
                intent.putExtra("idMk",listKelas.get(position).getIdMk() );
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKelas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtSemester;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtSemester = itemView.findViewById(R.id.txtSemester);
        }
    }
}

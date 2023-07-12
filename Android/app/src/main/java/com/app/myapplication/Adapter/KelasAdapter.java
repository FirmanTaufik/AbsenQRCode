package com.app.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.myapplication.Model.Home;
import com.app.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {
    private Context context;
    private List<Home.Kela> listKelas;

    public KelasAdapter(Context context, ArrayList<Home.Kela> listKelas) {
        this.context = context;
        this.listKelas = listKelas;
    }

    public interface Listener {
        void setOnClick(int idMk);
    }

    private Listener listener;

    public void setCallback(Listener listener){
        this.listener =listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_kelas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNama.setText(listKelas.get(position).getNamaKelas());
        holder.txtSemester.setText("Tahun Ajaran "+ listKelas.get(position).getTahunAjaran());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    listener.setOnClick(position);
                }

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

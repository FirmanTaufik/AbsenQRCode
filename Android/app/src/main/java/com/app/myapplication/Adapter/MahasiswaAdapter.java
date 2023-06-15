package com.app.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Model.Mahasiswa;
import com.app.myapplication.R;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private Context context;
    private List<Mahasiswa> mahasiswas;
    public interface Listener{
        void ItemClick(int position);
    }

    private  Listener listener;

    public void setListener( Listener listener){
        this.listener = listener;
    }
    public MahasiswaAdapter(Context context, List<Mahasiswa> listKelas) {
        this.context = context;
        this.mahasiswas = listKelas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNama.setText(mahasiswas.get(position).getNama());
        holder.txtNim.setText("NIM : "+ mahasiswas.get(position).getNim());
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtNim;
        private ImageButton img_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_delete = itemView.findViewById(R.id.img_delete);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtNim = itemView.findViewById(R.id.txtNim);
        }
    }
}

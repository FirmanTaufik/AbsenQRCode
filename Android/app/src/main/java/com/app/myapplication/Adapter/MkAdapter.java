package com.app.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Model.Home;
import com.app.myapplication.R;

import java.util.List;

public class MkAdapter extends RecyclerView.Adapter<MkAdapter.ViewHolder> {
    private Context context;
    private List<Home.Mengajar> listKelas;
    public interface Listener {
        void setOnClick(String idMk);
    }

    private Listener listener;

    public void setCallback(Listener listener){
        this.listener =listener;
    }
    public MkAdapter(Context context, List<Home.Mengajar> listKelas) {
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
        holder.imageView.setImageResource(R.drawable.baseline_backpack_24);
        holder.txtNama.setText(listKelas.get(position).getNamaMk());
        holder.txtSemester.setText("Kode MK : "+listKelas.get(position).getKodeMk());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    listener.setOnClick(listKelas.get(position).getIdMk());
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
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtSemester = itemView.findViewById(R.id.txtSemester);
        }
    }
}

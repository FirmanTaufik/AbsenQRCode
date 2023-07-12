package com.app.myapplication.Adapter;

import static com.app.myapplication.Retrofit.ApiClient.BASE_URL_IMAGE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Model.Mahasiswa;
import com.app.myapplication.Model.Post;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.Retrofit.GetService;
import com.app.myapplication.databinding.DialogMahasiswaBinding;
import com.app.myapplication.helper.Utils;
import com.app.myapplication.ui.KelasActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private Context context;
    private List<Mahasiswa> mahasiswas;

    public interface Listener {
        void ItemClick(int position);
        void ShowProfile(Mahasiswa data);
    }

    private Listener listener;

    public void setListener(Listener listener) {
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
        holder.txtNim.setText("NIM : " + mahasiswas.get(position).getNim());
        holder.txtTahunAjaran.setText("Tahun Ajaran : " + mahasiswas.get(position).getTahunAjaran());
        holder.txtTglLahir.setText("Tanggal Lahir : " + mahasiswas.get(position).getTempatTglLhr());
        holder.txtJurusan.setText("Jurusan : " + mahasiswas.get(position).getJurusan());
        holder.txtKelas.setText("Kelas : " + mahasiswas.get(position).getNamaKelas());
        if (mahasiswas.get(position).getFoto()!=null) {
            Utils.loadImage(ApiClient.BASE_URL_IMAGE+mahasiswas.get(position).getFoto().toString(), holder.image);
        }else   holder.image.setImageResource(R.drawable.ic_person);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ShowProfile(mahasiswas.get(position));
            }
        });
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
        private TextView txtNama, txtNim,txtTglLahir,txtJurusan,txtKelas
                ,txtTahunAjaran ;
        private ImageButton img_delete;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            txtTahunAjaran = itemView.findViewById(R.id.txtTahunAjaran);
            txtKelas = itemView.findViewById(R.id.txtKelas);
            txtJurusan = itemView.findViewById(R.id.txtJurusan);
            txtTglLahir = itemView.findViewById(R.id.txtTglLahir);
            img_delete = itemView.findViewById(R.id.img_delete);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtNim = itemView.findViewById(R.id.txtNim);
        }
    }
}

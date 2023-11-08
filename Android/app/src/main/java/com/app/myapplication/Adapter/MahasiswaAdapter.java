package com.app.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Model.Mahasiswa;
import com.app.myapplication.R;
import com.app.myapplication.Retrofit.ApiClient;
import com.app.myapplication.helper.Utils;

import java.util.List;

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
       // holder.txtTahunAjaran.setText("Tahun Ajaran : " + mahasiswas.get(position).getTahunAjaran());
        holder.txtTglLahir.setText("Tanggal Lahir : " + mahasiswas.get(position).getTempatTglLhr());
        holder.txtJurusan.setText("Jurusan : " + mahasiswas.get(position).getJurusan());
       // holder.txtKelas.setText("Kelas : " + mahasiswas.get(position).getNamaKelas());
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

        String[] arraySpinner = new String[] {
                "Tanpa Keterangan",    "Ijin", "Sakit", "Hadir"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        holder.spinner.setSelection(getStatus(mahasiswas.get(position).getStatus()));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String value = arraySpinner[pos].toLowerCase();
                switch (value) {
                    case "hadir" :
                        mahasiswas.get(position).setStatus(1);
                        break;
                    case "sakit" :
                        mahasiswas.get(position).setStatus(2);
                        break;
                    case "ijin" :
                        mahasiswas.get(position).setStatus(3);
                        break;
                    case "tanpa keterangan" :
                        mahasiswas.get(position).setStatus(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private Integer getStatus(int status){
        if (status == 1 ){
            return 3;
        }
        if (status == 3 ){
            return 1;
        }

        if (status==0) {
            return 0;
        }

      return   2;
    }


    @Override
    public int getItemCount() {
        return mahasiswas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       /* private TextView txtNama, txtNim,txtTglLahir,txtJurusan,txtKelas
                ,txtTahunAjaran ;*/

        private TextView txtNama, txtNim,txtTglLahir,txtJurusan;
        private ImageButton img_delete;
        private ImageView image;
        private Spinner spinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
          /*  txtTahunAjaran = itemView.findViewById(R.id.txtTahunAjaran);
            txtKelas = itemView.findViewById(R.id.txtKelas);*/
            txtJurusan = itemView.findViewById(R.id.txtJurusan);
            txtTglLahir = itemView.findViewById(R.id.txtTglLahir);
            img_delete = itemView.findViewById(R.id.img_delete);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtNim = itemView.findViewById(R.id.txtNim);
            spinner = itemView.findViewById(R.id.spinner);
        }
    }
}

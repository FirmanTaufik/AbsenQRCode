package com.app.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Model.DetailKelas;
import com.app.myapplication.R;

import java.util.List;

public class PertemuanAdapter extends RecyclerView.Adapter<PertemuanAdapter.ViewHolder> {
    private Context context;
    private List<DetailKelas.Pertemuan> list;

    public interface Listener{
        void ItemClick(DetailKelas.Pertemuan pertemuan);
        void onEdit(int position);
        void OnDelete(int position);

    }

    private Listener listener;

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public PertemuanAdapter(Context context, List<DetailKelas.Pertemuan> listKelas) {
        this.context = context;
        this.list = listKelas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_pertemuan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNama.setText("Pertemuan "+list.get(position).getPertemuan());
        holder.txtNim.setText( list.get(position).getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ItemClick(list.get(position));
            }
        });
        holder.img_delete.setColorFilter(ContextCompat.getColor(context, R.color.green_200), android.graphics.PorterDuff.Mode.SRC_IN);
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,    holder.img_delete);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.edit) listener.onEdit(position);
                        else listener.OnDelete(position);
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        holder.img_delete.setImageResource(R.drawable.baseline_more_vert_24);
    }

    @Override
    public int getItemCount() {
        return list.size();
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

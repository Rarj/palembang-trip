package dev.ri0arjuna.palembangtrip.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dev.ri0arjuna.palembangtrip.R;
import dev.ri0arjuna.palembangtrip.model.ModelSubFeatures;

public class AdapterSubFeatures extends RecyclerView.Adapter<AdapterSubFeatures.Holder> {

    Context context;
    List<ModelSubFeatures> modelSubFeaturesList;

    public AdapterSubFeatures(Context context, List<ModelSubFeatures> modelSubFeaturesList) {
        this.context = context;
        this.modelSubFeaturesList = modelSubFeaturesList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sub_fitur, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ModelSubFeatures modelSubFeatures = modelSubFeaturesList.get(position);

        holder.textViewNamaLokasi.setText(modelSubFeatures.getNama());
    }

    @Override
    public int getItemCount() {
        return modelSubFeaturesList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textViewNamaLokasi;
        public Holder(View itemView) {
            super(itemView);
            textViewNamaLokasi = itemView.findViewById(R.id.tv_nama_lokasi_sub);
        }
    }
}

package dev.ri0arjuna.palembangtrip.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.ri0arjuna.palembangtrip.MainActivity;
import dev.ri0arjuna.palembangtrip.R;
import dev.ri0arjuna.palembangtrip.model.ModelMainFeatures;
import dev.ri0arjuna.palembangtrip.view.SubFeaturesActivity;

public class AdapterMainFeatures extends RecyclerView.Adapter<AdapterMainFeatures.Holder> {

    Context context;
    List<ModelMainFeatures> modelMainFeatures;

    public AdapterMainFeatures(Context context, List<ModelMainFeatures> modelMainFeatures) {
        this.context = context;
        this.modelMainFeatures = modelMainFeatures;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_features, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final ModelMainFeatures features = modelMainFeatures.get(position);

        Glide.with(context)
                .load(features.getGambar())
                .into(holder.imageView);
        holder.textView.setText(features.getCount());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubFeaturesActivity.class);
                intent.putExtra("id", features.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelMainFeatures.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_main_features);
            imageView = itemView.findViewById(R.id.img_main_features);
            textView = itemView.findViewById(R.id.tv_count_features);
        }
    }
}

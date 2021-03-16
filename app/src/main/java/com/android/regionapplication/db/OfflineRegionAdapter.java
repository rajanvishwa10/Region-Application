package com.android.regionapplication.db;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.android.regionapplication.R;

import java.util.List;

public class OfflineRegionAdapter extends RecyclerView.Adapter<OfflineRegionAdapter.ViewHolder> {
    Context context;
    Activity activity;
    List<RegionModel> regionList;

    public OfflineRegionAdapter(Context context, List<RegionModel> regionList, Activity activity) {
        this.context = context;
        this.regionList = regionList;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_region, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegionModel regionModel = regionList.get(position);
        holder.nameTextView.setText(regionModel.name);
        String capital = regionModel.capital;
        if (!capital.trim().isEmpty()) {
            holder.capitalTextView.setVisibility(View.VISIBLE);
            holder.capitalTextView.setText(capital);
        } else {
            holder.capitalTextView.setVisibility(View.GONE);
        }
        String regionString = regionModel.region;
        if (!regionString.trim().isEmpty()) {
            holder.regionTextView.setVisibility(View.VISIBLE);
            holder.regionTextView.setText(regionString);
        } else {
            holder.regionTextView.setVisibility(View.GONE);
        }
        String subRegion = regionModel.subregion;
        if (!subRegion.trim().isEmpty()) {
            holder.subregionTextView.setVisibility(View.VISIBLE);
            holder.subregionTextView.setText(subRegion);
        } else {
            holder.subregionTextView.setText(View.GONE);
        }
        String population = regionModel.population;
        if (!population.trim().isEmpty()) {
            holder.populTextView.setVisibility(View.VISIBLE);
            holder.populTextView.setText(population);
        } else {
            holder.populTextView.setVisibility(View.GONE);
        }
        SvgLoader.pluck()
                .with(activity)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(regionModel.flag, holder.imageView);
        String border = regionModel.border.toString().trim();
        border = border.substring(1, border.length() - 1);
        String language = regionModel.languages.toString().trim();
        if (!border.isEmpty()) {
            holder.borderTextView.setVisibility(View.VISIBLE);
            holder.borderTextView.setText(border);
        } else {
            holder.borderTextView.setVisibility(View.GONE);
        }
        if (!language.isEmpty()) {
            holder.langTextView.setVisibility(View.VISIBLE);
            holder.langTextView.setText(language.substring(1, language.length() - 1));
        } else {
            holder.langTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, capitalTextView, regionTextView, subregionTextView, populTextView,
                borderTextView, langTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.flagImageView);
            nameTextView = itemView.findViewById(R.id.name);
            capitalTextView = itemView.findViewById(R.id.capital);
            regionTextView = itemView.findViewById(R.id.region);
            subregionTextView = itemView.findViewById(R.id.subregion);
            populTextView = itemView.findViewById(R.id.population);
            borderTextView = itemView.findViewById(R.id.borders);
            langTextView = itemView.findViewById(R.id.languages);
        }
    }
}

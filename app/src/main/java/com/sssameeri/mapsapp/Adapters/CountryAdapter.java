package com.sssameeri.mapsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sssameeri.mapsapp.Models.Region;
import com.sssameeri.mapsapp.Network.NetworkService;
import com.sssameeri.mapsapp.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private List<Region> regionList;
    private Context context;

    public CountryAdapter(List<Region> regionList, Context context) {
        this.regionList = regionList;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_list_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Region region = regionList.get(position);
        holder.name.setText(region.getName());
    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        private TextView name;
        private ImageView downloadImageView;
        private ProgressBar downloadProgressBar;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.countryTextView);
            downloadImageView = itemView.findViewById(R.id.downloadImageView);
            downloadProgressBar = itemView.findViewById(R.id.downloadProgressBar);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            downloadProgressBar.setVisibility(View.VISIBLE);
            Region region = regionList.get(position);
            NetworkService networkService = new NetworkService(context);
            networkService.downloadFile(region);

        }

    }

}

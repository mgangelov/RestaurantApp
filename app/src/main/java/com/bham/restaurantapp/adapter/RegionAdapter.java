package com.bham.restaurantapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.model.Region;

import java.util.List;

public class RegionAdapter extends ArrayAdapter<Region> {
    private Context context;
    private List<Region> regionsList;


    public RegionAdapter(@NonNull Context context, int resource, @NonNull List<Region> objects) {
        super(context, resource, objects);
        this.context = context;
        this.regionsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(this.context).inflate(R.layout.list_item, parent, false);

        Region currentRegion = this.regionsList.get(position);
        TextView regionNameTextView = listItem.findViewById(R.id.regionNameTextView);
        regionNameTextView.setText(String.format("Region name: %s", currentRegion.getName()));
        TextView regionCodeTextView = listItem.findViewById(R.id.regionCodeTextView);
        regionCodeTextView.setText(String.format("Region code: %s", currentRegion.getCode()));

        return listItem;
    }
}

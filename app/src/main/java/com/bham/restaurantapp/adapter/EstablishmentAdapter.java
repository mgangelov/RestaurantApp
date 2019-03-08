package com.bham.restaurantapp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import com.bham.restaurantapp.R;
import com.bham.restaurantapp.model.Establishment;

import java.util.List;

public class EstablishmentAdapter extends ArrayAdapter<Establishment> {
    private Context context;
    private List<Establishment> establishmentsList;


    public EstablishmentAdapter(@NonNull Context context, int resource, @NonNull List<Establishment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.establishmentsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(this.context).inflate(R.layout.establishment_list_item, parent, false);

        Establishment establishment = this.establishmentsList.get(position);
        TextView establishmentNameTextView = listItem.findViewById(R.id.establishmentNameTextView);
        establishmentNameTextView.setText(String.format("Name: %s", establishment.getBusinessName()));
        TextView establishmentRatingTextView = listItem.findViewById(R.id.establishmentRatingTextView);
        establishmentRatingTextView.setText(String.format("Rating: %s", establishment.getRatingValue()));

        return listItem;
    }
}

package com.bham.restaurantapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.model.Establishment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder> {
    private List<Establishment> establishmentsList;

    public EstablishmentAdapter(List<Establishment> establishmentsList) {
        this.establishmentsList = establishmentsList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.test_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(establishmentsList.get(position).getBusinessName());
    }

    @Override
    public int getItemCount() {
        return this.establishmentsList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(@NonNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }

}

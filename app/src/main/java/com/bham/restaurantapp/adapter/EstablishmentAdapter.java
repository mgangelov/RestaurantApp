package com.bham.restaurantapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bham.restaurantapp.R;
import com.bham.restaurantapp.model.fsa.Establishment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder> {
    private List<Establishment> establishmentsList;

    public EstablishmentAdapter(List<Establishment> establishmentsList) {
        this.establishmentsList = establishmentsList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.establishment_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.constraintLayout.setText(establishmentsList.get(position).getBusinessName());
        holder.establishmentNameTextView.setText(establishmentsList.get(position).getBusinessName());
        holder.establishmentRatingBar.setRating(Float.parseFloat(this.establishmentsList.get(position).getRatingValue()));
        String establishmentDistance = this.establishmentsList.get(position).getDistance();
        if (establishmentDistance != null) {
            holder.establishmentDistTextView.setText(establishmentDistance);
        }
    }

    @Override
    public int getItemCount() {
        return this.establishmentsList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ConstraintLayout ratingNameRatingContainer;
        TextView establishmentNameTextView;
        RatingBar establishmentRatingBar;
        TextView establishmentDistTextView;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.establishmentItemConstraintLayout);
            ratingNameRatingContainer = itemView.findViewById(R.id.ratingNameRatingContainer);
            establishmentNameTextView = itemView.findViewById(R.id.establishmentNameTextView);
            establishmentRatingBar = itemView.findViewById(R.id.establishmentRatingBar);
            establishmentDistTextView = itemView.findViewById(R.id.establishmentDistTextView);
        }
    }

}

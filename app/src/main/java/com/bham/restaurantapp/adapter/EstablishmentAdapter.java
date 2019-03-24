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
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public class EstablishmentAdapter extends RecyclerView.Adapter<EstablishmentAdapter.MyViewHolder> {
    private List<Establishment> establishmentsList;
    private SelectionTracker<Establishment> selectionTracker;

    public EstablishmentAdapter(List<Establishment> establishmentsList) {
        this.setHasStableIds(true);
        this.establishmentsList = establishmentsList;
    }

    public void setSelectionTracker(SelectionTracker<Establishment> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public SelectionTracker<Establishment> getSelectionTracker() {
        return selectionTracker;
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
        holder.establishmentNameTextView.setText(establishmentsList.get(position).getBusinessName());
        try {
            holder.establishmentRatingBar.setRating(Float.parseFloat(this.establishmentsList.get(position).getRatingValue()));
        } catch (NumberFormatException n) {
            holder.establishmentRatingBar.setRating(0);
        }
        String establishmentDistance = this.establishmentsList.get(position).getDistance();
        if (establishmentDistance != null) {
            holder.establishmentDistTextView.setText(establishmentDistance);
        }
        Establishment currentItem = establishmentsList.get(position);
        holder.bind(currentItem, selectionTracker.isSelected(currentItem));
    }

    @Override
    public int getItemCount() {
        return this.establishmentsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView establishmentNameTextView;
        RatingBar establishmentRatingBar;
        TextView establishmentDistTextView;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            establishmentNameTextView = itemView.findViewById(R.id.establishmentNameTextView);
            establishmentRatingBar = itemView.findViewById(R.id.establishmentRatingBar);
            establishmentDistTextView = itemView.findViewById(R.id.establishmentDistTextView);
        }

        ItemDetailsLookup.ItemDetails<Establishment> getItemDetails() {
            return new EstablishmentDetail(
                    getAdapterPosition(),
                    establishmentsList.get(getAdapterPosition())
            );
        }

        public void bind(Establishment currentItem, boolean selected) {
           itemView.setActivated(selected);
        }
    }

}


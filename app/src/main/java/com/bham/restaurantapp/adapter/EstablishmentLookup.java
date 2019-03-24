package com.bham.restaurantapp.adapter;

import android.view.MotionEvent;
import android.view.View;

import com.bham.restaurantapp.model.fsa.Establishment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class EstablishmentLookup extends ItemDetailsLookup<Establishment> {
    private final RecyclerView recyclerView;

    public EstablishmentLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Establishment> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof EstablishmentAdapter.MyViewHolder) {
//                return ((EstablishmentAdapter.MyViewHolder) viewHolder).getItemDetails();
            }
        }
        return null;
    }
}

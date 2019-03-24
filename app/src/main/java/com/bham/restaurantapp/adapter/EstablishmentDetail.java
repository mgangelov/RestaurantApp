package com.bham.restaurantapp.adapter;

import com.bham.restaurantapp.model.fsa.Establishment;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class EstablishmentDetail extends ItemDetailsLookup.ItemDetails<Establishment> {
    private int adapterPosition;
    private final Establishment selectionKey;

    EstablishmentDetail(int adapterPosition, Establishment selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Establishment getSelectionKey() {
        return selectionKey;
    }
}

package com.bham.restaurantapp.adapter;

import com.bham.restaurantapp.model.fsa.Establishment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class EstablishmentKeyProvider extends ItemKeyProvider<Establishment> {
    private final List<Establishment> establishments;

    /**
     * Creates a new provider with the given scope.
     *
     * @param scope Scope can't be changed at runtime.
     */
    public EstablishmentKeyProvider(int scope, List<Establishment> establishments) {
        super(scope);
        this.establishments = establishments;
    }

    @Nullable
    @Override
    public Establishment getKey(int position) {
        return establishments.get(position);
    }

    @Override
    public int getPosition(@NonNull Establishment key) {
        return establishments.indexOf(key);
    }
}

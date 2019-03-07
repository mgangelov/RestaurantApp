package com.bham.restaurantapp.model;

import java.util.List;

public class EstablishmentResult {
    private List<Establishment> establishments;
    private ResponseMetadata meta;

    public List<Establishment> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<Establishment> establishments) {
        this.establishments = establishments;
    }

    public ResponseMetadata getMeta() {
        return meta;
    }

    public void setMeta(ResponseMetadata meta) {
        this.meta = meta;
    }
}

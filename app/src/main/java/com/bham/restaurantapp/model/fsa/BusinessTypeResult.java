package com.bham.restaurantapp.model.fsa;

import java.util.List;

public class BusinessTypeResult {
    private List<BusinessType> businessTypes;
    private ResponseMetadata meta;

    public List<BusinessType> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<BusinessType> businessTypes) {
        this.businessTypes = businessTypes;
    }

    public ResponseMetadata getMeta() {
        return meta;
    }

    public void setMeta(ResponseMetadata meta) {
        this.meta = meta;
    }
}

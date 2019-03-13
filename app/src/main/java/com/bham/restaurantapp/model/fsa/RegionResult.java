package com.bham.restaurantapp.model.fsa;

import java.util.List;

public class RegionResult {
    private List<Region> regions;
    private ResponseMetadata metadata;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public ResponseMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ResponseMetadata metadata) {
        this.metadata = metadata;
    }
}

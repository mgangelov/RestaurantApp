package com.bham.restaurantapp.model.fsa;

import java.util.List;

public class SortOptionResult {
    private List<SortOption> sortOptions;
    private ResponseMetadata meta;

    public List<SortOption> getSortOptions() {
        return sortOptions;
    }

    public void setSortOptions(List<SortOption> sortOptions) {
        this.sortOptions = sortOptions;
    }

    public ResponseMetadata getMeta() {
        return meta;
    }

    public void setMeta(ResponseMetadata meta) {
        this.meta = meta;
    }
}

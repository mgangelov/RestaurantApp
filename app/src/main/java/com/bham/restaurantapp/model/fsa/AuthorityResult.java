package com.bham.restaurantapp.model.fsa;

import java.util.List;

public class AuthorityResult {
    private List<Authority> authorities;
    private ResponseMetadata meta;

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public ResponseMetadata getMeta() {
        return meta;
    }

    public void setMeta(ResponseMetadata meta) {
        this.meta = meta;
    }
}

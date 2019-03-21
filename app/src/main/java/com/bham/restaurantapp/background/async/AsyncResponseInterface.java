package com.bham.restaurantapp.background.async;

public interface AsyncResponseInterface<T> {
    void processResponse(T output);
}

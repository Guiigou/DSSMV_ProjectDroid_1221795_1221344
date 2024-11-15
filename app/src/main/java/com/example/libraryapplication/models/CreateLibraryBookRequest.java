package com.example.libraryapplication.models;

import com.google.gson.annotations.SerializedName;

public class CreateLibraryBookRequest {
    @SerializedName("stock")
    private int stock;

    public CreateLibraryBookRequest(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

package com.example.libraryapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface api {
    @GET("libraries")
        // coloque o caminho correto da sua API aqui
    Call<List<Library>> getLibraries();
}
package com.example.libraryapplication.api;

import java.util.List;

import com.example.libraryapplication.dto.LibraryDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LibraryAPI {
    @GET("/v1/library")  // Ajusta o endpoint conforme a API fornecida
    Call<List<LibraryDTO>> getLibraries();
}

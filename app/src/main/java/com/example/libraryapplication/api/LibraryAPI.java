package com.example.libraryapplication.api;

import com.example.libraryapplication.models.Library;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LibraryAPI {
    @GET("/v1/library")
    Call<List<Library>> getLibraries();
}

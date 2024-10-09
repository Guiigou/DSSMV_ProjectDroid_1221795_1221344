package com.example.libraryapplication.services;

import com.example.libraryapplication.models.Library;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LibraryService {

    @POST("/v1/library")
    Call<Void> createLibrary(@Body Library library);

    @GET("/v1/library")
    Call<List<Library>> getLibraries();
}

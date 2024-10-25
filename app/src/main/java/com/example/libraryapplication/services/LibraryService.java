package com.example.libraryapplication.services;

import com.example.libraryapplication.models.Library;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface LibraryService {

    @GET("/v1/library")
    Call<List<Library>> getLibraries();

    @GET("/v1/library/{id}")
    Call<Library> getLibrary(@Path("id") String libraryId);

    @POST("/v1/library")
    Call<Void> createLibrary(@Body Library library);

    @PUT("/v1/library/{id}")
    Call<Void> updateLibrary(@Path("id") String libraryId, @Body Library library);

    @DELETE("/v1/library/{id}")
    Call<Void> deleteLibrary(@Path("id") String libraryId);
}

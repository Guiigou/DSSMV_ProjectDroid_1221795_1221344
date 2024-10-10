package com.example.libraryapplication.services;

import com.example.libraryapplication.models.Library;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface LibraryService {

    @DELETE("/v1/library/{id}")
    Call<Void> deleteLibrary(@Path("id") String libraryId);

    @POST("/v1/library")
    Call<Void> createLibrary(@Body Library library);

    @GET("/v1/library")
    Call<List<Library>> getLibraries();
}

package com.example.libraryapplication.services;

import com.example.libraryapplication.models.CheckedOutBook;
import com.example.libraryapplication.models.UserRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface LibraryBookCheckout {

    @POST("/v1/library/{libraryId}/book/{isbn}/checkout")
    Call<Void> checkOutBook(
            @Path("libraryId") String libraryId,
            @Path("isbn") String isbn,
            @Body UserRequest userRequest
    );

    @POST("/v1/library/{libraryId}/book/{isbn}/checkin")
    Call<Void> checkInBook(
            @Path("libraryId") String libraryId,
            @Path("isbn") String isbn,
            @Body UserRequest userRequest
    );

    @GET("/v1/user/checked-out")
    Call<List<CheckedOutBook>> getCheckedOutBooks(
            @Query("userId") String userId
    );
}

package com.example.libraryapplication.services;

import com.example.libraryapplication.models.Book;
import com.example.libraryapplication.models.LibraryBook;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface LibraryBookService {

    @GET("/v1/library/{libraryId}/book")
    Call<List<LibraryBook>> getBooks(@Path("libraryId") String libraryId);

    @GET("/v1/library/{libraryId}/book/{isbn}")
    Call<LibraryBook> getBook(@Path("libraryId") String libraryId, @Path("isbn") String isbn);

    @POST("/v1/library/{libraryId}/book/{isbn}")
    Call<Void> createBook(@Path("libraryId") String libraryId, @Path("isbn") String isbn, @Body Book book);
}

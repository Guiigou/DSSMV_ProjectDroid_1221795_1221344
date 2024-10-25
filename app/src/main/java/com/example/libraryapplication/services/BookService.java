package com.example.libraryapplication.services;

import com.example.libraryapplication.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface BookService {

    @GET("/v1/library/{libraryId}/book")
    Call<List<Book>> getBooks(@Path("libraryId") String libraryId);

    @GET("/v1/library/{libraryId}/book/{isbn}")
    Call<Book> getBook(@Path("libraryId") String libraryId, @Path("isbn") String isbn);

    @POST("/v1/library/{libraryId}/book/{isbn}")
    Call<Void> createBook(@Path("libraryId") String libraryId, @Path("isbn") String isbn, @Body Book book);

    @PUT("/v1/library/{libraryId}/book/{isbn}")
    Call<Void> updateBook(@Path("libraryId") String libraryId, @Path("isbn") String isbn, @Body Book book);
}

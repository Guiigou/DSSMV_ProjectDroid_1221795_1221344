package com.example.libraryapplication.services;

import com.example.libraryapplication.models.Book;
import java.util.List;

import com.example.libraryapplication.models.BookDTO;
import retrofit2.Call;
import retrofit2.http.*;

public interface BookService {

    @GET("/v1/book/{isbn}")
    Call<Book> loadBook(@Path("isbn") String isbn);

}

package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.adapters.BookAdapter;
import com.example.libraryapplication.models.Book;
import com.example.libraryapplication.models.BookDTO;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.BookService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private List<BookDTO> booksList;
    private String libraryId;
    private Button btnAddBook;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        // Obter o ID da biblioteca do Intent
        libraryId = getIntent().getStringExtra("libraryId");

        if (libraryId == null) {
            Toast.makeText(this, "Erro: ID da biblioteca não foi passado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Carregar os livros da biblioteca
        loadBooks();
    }

    // Método para carregar livros
    private void loadBooks() {
        BookService bookService = ApiClient.getClient().create(BookService.class);
        Call<List<BookDTO>> call = bookService.getBooks(libraryId);

        call.enqueue(new Callback<List<BookDTO>>() {
            @Override
            public void onResponse(Call<List<BookDTO>> call, Response<List<BookDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    if (response.isSuccessful()) {
                        Log.d("BooksActivity", "Resposta bem-sucedida: " + response.body());
                    } else {
                        Log.e("BooksActivity", "Erro na resposta: " + response.errorBody());
                    }


                    if (response.body() != null) {
                        Log.d("BooksActivity", "Resposta JSON: " + response.body().toString());
                    } else {
                        Log.e("BooksActivity", "O corpo da resposta está nulo.");
                    }

                    List<BookDTO> books = response.body();
                    Log.d("BooksActivity", "Número de livros recebidos: " + books.size());

                    for (BookDTO book : books) {
                        Log.d("BooksActivity", "Título: " + book.getTitle() + ", Autores: " + book.getAuthors());
                    }

                    List<BookDTO> bookDTOList = new ArrayList<>();
                    for (BookDTO book : books) {
                        bookDTOList.add(new BookDTO(book.getTitle(), book.getAuthors()));
                    }

                    if (bookDTOList.isEmpty()) {
                        Toast.makeText(BooksActivity.this, "Nenhum livro encontrado.", Toast.LENGTH_LONG).show();
                    } else {
                        bookAdapter = new BookAdapter(BooksActivity.this, bookDTOList);
                        recyclerViewBooks.setAdapter(bookAdapter);
                    }
                } else {
                    Toast.makeText(BooksActivity.this, "Erro ao carregar livros. Código: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.d("BooksActivity", "Erro na resposta da API. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<BookDTO>> call, Throwable t) {
                Toast.makeText(BooksActivity.this, "Erro ao carregar livros: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("BooksActivity", "Erro na chamada da API: " + t.getMessage());
            }

        });
    }

}

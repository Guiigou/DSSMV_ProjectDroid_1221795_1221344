package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.adapters.BookAdapter;
import com.example.libraryapplication.models.Book;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.BookService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private List<Book> booksList;
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
        Call<List<Book>> call = bookService.getBooks(libraryId);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    booksList = response.body();
                    if (booksList.isEmpty()) {
                        Toast.makeText(BooksActivity.this, "Nenhum livro encontrado.", Toast.LENGTH_LONG).show();
                    } else {
                        bookAdapter = new BookAdapter(BooksActivity.this, booksList);
                        recyclerViewBooks.setAdapter(bookAdapter);
                    }
                } else {
                    Toast.makeText(BooksActivity.this, "Erro ao carregar livros. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(BooksActivity.this, "Erro ao carregar livros: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}

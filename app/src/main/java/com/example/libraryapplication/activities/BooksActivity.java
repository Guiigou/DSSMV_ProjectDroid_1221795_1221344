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

        btnAddBook = findViewById(R.id.btnAddBook);
        btnBack = findViewById(R.id.btnBackToLibraries);

        // Obtém o ID da biblioteca a partir do Intent
        libraryId = getIntent().getStringExtra("libraryId");

        // Carrega os livros da biblioteca
        loadBooks();

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para adicionar um livro
                Intent intent = new Intent(BooksActivity.this, AddBookActivity.class);
                intent.putExtra("libraryId", libraryId);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fecha a activity atual e volta para a anterior
                finish();
            }
        });
    }

    private void loadBooks() {
        if (libraryId == null || libraryId.isEmpty()) {
            Toast.makeText(this, "Erro: ID da biblioteca é inválido", Toast.LENGTH_LONG).show();
            return;
        }

        BookService bookService = ApiClient.getClient().create(BookService.class);
        Call<List<Book>> call = bookService.getBooks(libraryId);

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    booksList = response.body();
                    bookAdapter = new BookAdapter(BooksActivity.this, booksList);
                    recyclerViewBooks.setAdapter(bookAdapter);
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

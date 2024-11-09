package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.adapters.BookAdapter;
import com.example.libraryapplication.models.LibraryBook;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryBookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private String libraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        libraryId = getIntent().getStringExtra("libraryId");
        Log.d("BooksActivity", "Library ID recebido: " + libraryId);

        if (libraryId == null) {
            Toast.makeText(this, "Erro: ID da biblioteca não foi passado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        loadBooks();
    }

    private void loadBooks() {
        LibraryBookService libraryBookService = ApiClient.getClient().create(LibraryBookService.class);
        Call<List<LibraryBook>> call = libraryBookService.getBooks(libraryId);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LibraryBook> books = response.body();
                    if (books.isEmpty()) {
                        Toast.makeText(BooksActivity.this, "Nenhum livro encontrado.", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("BooksActivity", "Número de livros recebidos: " + books.size());
                        bookAdapter = new BookAdapter(BooksActivity.this, books);
                        recyclerViewBooks.setAdapter(bookAdapter);
                    }
                } else {
                    Toast.makeText(BooksActivity.this, "Erro ao carregar livros. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LibraryBook>> call, Throwable t) {
                Toast.makeText(BooksActivity.this, "Erro ao carregar livros: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

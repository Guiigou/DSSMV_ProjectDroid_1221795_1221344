package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.Author;
import com.example.libraryapplication.models.Book;
import com.example.libraryapplication.models.CoverUrls;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryBookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    private EditText edtBookTitle;
    private EditText edtBookAuthor;
    private EditText edtBookISBN;
    private Button btnSaveBook;
    private Button btnBack;
    private String libraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        edtBookTitle = findViewById(R.id.edtBookTitle);
        edtBookAuthor = findViewById(R.id.edtBookAuthor);
        edtBookISBN = findViewById(R.id.edtBookISBN);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        btnBack = findViewById(R.id.btnBack);

        libraryId = getIntent().getStringExtra("libraryId");

        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtBookTitle.getText().toString();
                String authorName = edtBookAuthor.getText().toString();
                String isbn = edtBookISBN.getText().toString();

                if (title.isEmpty() || authorName.isEmpty() || isbn.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_LONG).show();
                } else {
                    List<Author> authors = new ArrayList<>();
                    authors.add(new Author(null, authorName, null, null, null, null));

                    CoverUrls coverUrls = new CoverUrls(); // Pode deixar vazio para agora
                    Book newBook = new Book(title, "", isbn, "", 0, coverUrls, authors);

                    addBook(newBook);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addBook(Book book) {
        LibraryBookService libraryBookService = ApiClient.getClient().create(LibraryBookService.class);
        Call<Void> call = libraryBookService.createBook(libraryId, book.getIsbn(), book);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddBookActivity.this, "Livro adicionado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AddBookActivity.this, "Erro ao adicionar livro. Verifique os dados.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddBookActivity.this, "Erro ao adicionar livro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

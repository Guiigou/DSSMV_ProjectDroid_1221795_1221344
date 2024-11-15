package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.CreateLibraryBookRequest;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryBookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {

    private EditText etBookIsbn, etBookStock;
    private String libraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Inicializar as views
        etBookIsbn = findViewById(R.id.etBookIsbn);
        etBookStock = findViewById(R.id.etBookStock);
        Button btnAddBook = findViewById(R.id.btnAddBook);

        libraryId = getIntent().getStringExtra("libraryId");

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = etBookIsbn.getText().toString().trim();
                String stockString = etBookStock.getText().toString().trim();

                if (isbn.isEmpty() || stockString.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    int stock = Integer.parseInt(stockString);
                    addBook(isbn, stock);
                }
            }
        });
    }

    private void addBook(String isbn, int stock) {
        LibraryBookService libraryBookService = ApiClient.getClient().create(LibraryBookService.class);
        CreateLibraryBookRequest request = new CreateLibraryBookRequest(stock);
        Call<Void> call = libraryBookService.createBook(libraryId, isbn, request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddBookActivity.this, "Book added successfully!", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddBookActivity.this, "Error adding book. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddBookActivity.this, "Error adding book: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

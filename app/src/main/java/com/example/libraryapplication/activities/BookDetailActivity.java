package com.example.libraryapplication.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.LibraryBook;
import com.example.libraryapplication.models.UserRequest;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryBookCheckout;
import com.example.libraryapplication.services.LibraryBookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvBookTitle;
    private TextView tvBookAuthor;
    private TextView tvBookDescription;
    private TextView tvBookStock;
    private String libraryId;
    private String isbn;
    private LibraryBook currentLibraryBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivBookCover = findViewById(R.id.ivBookCover);
        tvBookTitle = findViewById(R.id.tvBookTitle);
        tvBookAuthor = findViewById(R.id.tvBookAuthor);
        tvBookDescription = findViewById(R.id.tvBookDescription);
        tvBookStock = findViewById(R.id.tvBookStock);

        // Receber dados da Intent
        libraryId = getIntent().getStringExtra("libraryId");
        isbn = getIntent().getStringExtra("isbn");
        String title = getIntent().getStringExtra("title");
        String authors = getIntent().getStringExtra("authors");
        String description = getIntent().getStringExtra("description");
        String coverUrl = getIntent().getStringExtra("coverUrl");

        // Exibir dados recebidos
        if (title != null) tvBookTitle.setText(title);
        if (authors != null) tvBookAuthor.setText(authors);
        if (description != null) tvBookDescription.setText(description);

        if (coverUrl != null && !coverUrl.isEmpty()) {
            Glide.with(this).load(coverUrl).into(ivBookCover);
        } else {
            Toast.makeText(this, "Capa do livro não disponível", Toast.LENGTH_SHORT).show();
        }

        // Buscar informações do estoque atual
        carregarLivro();

        // Configurar ação de check-out
        findViewById(R.id.btnCheckOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirDialogoUsuario("check-out");
            }
        });

        // Configurar ação de check-in
        findViewById(R.id.btnCheckIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirDialogoUsuario("check-in");
            }
        });
    }

    private void carregarLivro() {
        LibraryBookService service = ApiClient.getClient().create(LibraryBookService.class);
        Call<LibraryBook> call = service.getBook(libraryId, isbn);

        call.enqueue(new Callback<LibraryBook>() {
            @Override
            public void onResponse(Call<LibraryBook> call, Response<LibraryBook> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentLibraryBook = response.body();
                    atualizarEstoque(currentLibraryBook.getStock());
                } else {
                    Toast.makeText(BookDetailActivity.this, "Erro ao carregar informações do livro. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LibraryBook> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Erro ao carregar informações do livro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirDialogoUsuario(String acao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Inserir ID do Usuário");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userId = input.getText().toString().trim();
                if (userId.isEmpty()) {
                    Toast.makeText(BookDetailActivity.this, "Por favor, insira o ID do usuário.", Toast.LENGTH_SHORT).show();
                } else {
                    if (acao.equals("check-out")) {
                        realizarCheckOut(userId);
                    } else if (acao.equals("check-in")) {
                        realizarCheckIn(userId);
                    }
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void realizarCheckOut(String userId) {
        if (currentLibraryBook == null || currentLibraryBook.getAvailable() <= 0) {
            Toast.makeText(this, "O livro não está disponível para check-out.", Toast.LENGTH_LONG).show();
            return;
        }

        LibraryBookCheckout service = ApiClient.getClient().create(LibraryBookCheckout.class);
        UserRequest userRequest = new UserRequest(userId);
        Call<Void> call = service.checkOutBook(libraryId, isbn, userRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    currentLibraryBook.setAvailable(currentLibraryBook.getAvailable() - 1);
                    atualizarEstoque(currentLibraryBook.getStock());
                    Toast.makeText(BookDetailActivity.this, "Livro retirado com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BookDetailActivity.this, "Erro ao realizar o check-out. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Erro ao realizar o check-out: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void realizarCheckIn(String userId) {
        LibraryBookCheckout service = ApiClient.getClient().create(LibraryBookCheckout.class);
        UserRequest userRequest = new UserRequest(userId);
        Call<Void> call = service.checkInBook(libraryId, isbn, userRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    currentLibraryBook.setAvailable(currentLibraryBook.getAvailable() + 1);
                    atualizarEstoque(currentLibraryBook.getStock());
                    Toast.makeText(BookDetailActivity.this, "Livro devolvido com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BookDetailActivity.this, "Erro ao realizar o check-in. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Erro ao realizar o check-in: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void atualizarEstoque(int novoStock) {
        if (currentLibraryBook != null) {
            tvBookStock.setText("Stock: " + currentLibraryBook.getAvailable() + " de " + novoStock);
        }
    }
}

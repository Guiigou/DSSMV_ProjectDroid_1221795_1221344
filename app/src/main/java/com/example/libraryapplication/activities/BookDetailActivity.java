package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.libraryapplication.R;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
    private TextView tvBookTitle;
    private TextView tvBookAuthor;
    private TextView tvBookDescription;
    private TextView tvBookStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivBookCover = findViewById(R.id.ivBookCover);
        tvBookTitle = findViewById(R.id.tvBookTitle);
        tvBookAuthor = findViewById(R.id.tvBookAuthor);
        tvBookDescription = findViewById(R.id.tvBookDescription);
        tvBookStock = findViewById(R.id.tvBookStock);

        String title = getIntent().getStringExtra("title");
        String authors = getIntent().getStringExtra("authors");
        String description = getIntent().getStringExtra("description");
        int stock = getIntent().getIntExtra("stock", 0);
        String coverUrl = getIntent().getStringExtra("coverUrl");

        // Verificar se os dados foram recebidos corretamente
        Log.d("BookDetailActivity", "Title: " + title);
        Log.d("BookDetailActivity", "Authors: " + authors);
        Log.d("BookDetailActivity", "Description: " + description);
        Log.d("BookDetailActivity", "Stock: " + stock);
        Log.d("BookDetailActivity", "Cover URL recebida: " + coverUrl);

        if (title != null) tvBookTitle.setText(title);
        if (authors != null) tvBookAuthor.setText(authors);
        if (description != null) tvBookDescription.setText(description);
        tvBookStock.setText("Estoque: " + stock);

        if (coverUrl != null && !coverUrl.isEmpty()) {
            Glide.with(this)
                    .load(coverUrl)
                    .into(ivBookCover);
        } else {
            Log.d("BookDetailActivity", "URL da capa não disponível");
            Toast.makeText(this, "Capa do livro não disponível", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.libraryapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.libraryapplication.R;
import com.example.libraryapplication.activities.BookDetailActivity;
import com.example.libraryapplication.models.LibraryBook;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<LibraryBook> booksList;

    public BookAdapter(Context context, List<LibraryBook> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        LibraryBook libraryBook = booksList.get(position);

        if (libraryBook.getBook() != null) {
            holder.tvBookTitle.setText(libraryBook.getBookTitle());
            holder.tvBookAuthor.setText(libraryBook.getBookAuthors());

            try {
                String isbn = libraryBook.getIsbn().replaceAll("-", "");
                String encodedIsbn = URLEncoder.encode(isbn, "UTF-8");
                String coverUrl = "http://193.136.62.24/v1/assets/cover/" + encodedIsbn + "-M.jpg";

                Log.d("BookAdapter", "Cover URL: " + coverUrl); // Log para verificar a URL da capa

                Glide.with(context)
                        .load(coverUrl)
                        .into(holder.ivBookCover);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("BookAdapter", "Erro ao codificar a URL da imagem: " + e.getMessage());
            }
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d("BookAdapter", "Clicando no livro: " + libraryBook.getBookTitle());
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("libraryId", libraryBook.getLibrary().getId()); // Passando o ID da biblioteca
            intent.putExtra("isbn", libraryBook.getIsbn());
            intent.putExtra("title", libraryBook.getBookTitle());
            intent.putExtra("authors", libraryBook.getBookAuthors());
            intent.putExtra("description", libraryBook.getBookDescription());
            intent.putExtra("stock", libraryBook.getStock());

            // Enviar a URL da capa ao BookDetailActivity
            try {
                String isbn = libraryBook.getIsbn().replaceAll("-", "");
                String encodedIsbn = URLEncoder.encode(isbn, "UTF-8");
                String coverUrl = "http://193.136.62.24/v1/assets/cover/" + encodedIsbn + "-M.jpg";
                intent.putExtra("coverUrl", coverUrl);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return booksList != null ? booksList.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        TextView tvBookAuthor;
        ImageView ivBookCover;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
        }
    }
}

package com.example.libraryapplication.adapters;

import android.content.Context;
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
import com.example.libraryapplication.models.BookDTO;
import com.example.libraryapplication.models.Author;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<BookDTO> booksList;

    public BookAdapter(Context context, List<BookDTO> booksList) {
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
        BookDTO book = booksList.get(position);

        holder.tvBookTitle.setText(book.getTitle() != null ? book.getTitle() : "Título indisponível");

        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            StringBuilder authorsText = new StringBuilder();
            for (Author author : book.getAuthors()) {
                if (authorsText.length() > 0) {
                    authorsText.append(", ");
                }
                authorsText.append(author.getName());
            }
            holder.tvBookAuthor.setText(authorsText.toString());
        } else {
            holder.tvBookAuthor.setText("Autor desconhecido");
        }

        try {
            // Remove os traços do ISBN
            String isbn = book.getIsbn().replaceAll("-", ""); // Remove os traços do ISBN
            String encodedIsbn = URLEncoder.encode(isbn, "UTF-8");
            String coverUrl = "http://193.136.62.24/v1/assets/cover/" + encodedIsbn + "-M.jpg"; // Use a versão "Medium" da imagem para melhor qualidade

            Log.d("BookAdapter", "Cover URL: " + coverUrl);

            if (coverUrl != null) {
                Picasso.get()
                        .load(coverUrl)
                        .into(holder.ivBookCover, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("BookAdapter", "Imagem carregada com sucesso.");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("BookAdapter", "Erro ao carregar a imagem: " + e.getMessage());
                            }
                        });

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("BookAdapter", "Erro ao codificar a URL da imagem: " + e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return booksList != null ? booksList.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        TextView tvBookAuthor;
        ImageView ivBookCover; // Certifique-se de ter uma ImageView no layout

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            ivBookCover = itemView.findViewById(R.id.ivBookCover); // Adicione isso
        }
    }
}

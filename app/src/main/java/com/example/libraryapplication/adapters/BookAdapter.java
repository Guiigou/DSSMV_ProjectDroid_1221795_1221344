package com.example.libraryapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> booksList;

    public BookAdapter(Context context, List<Book> booksList) {
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
        Book book = booksList.get(position);
        holder.tvBookTitle.setText(book.getTitle());

        // Converte a lista de autores numa string separada por vírgulas
        String authorsText = String.join(", ", book.getAuthors());
        holder.tvBookAuthor.setText(authorsText);

        // Clique normal para abrir detalhes do livro ou outra ação
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para abrir detalhes do livro, se necessário
                Toast.makeText(context, "Livro: " + book.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
/*
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.book_item_menu); // Certifique-se de criar este menu XML

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_book:
                                // Lógica para editar o livro
                                Intent editIntent = new Intent(context, EditBookActivity.class);
                                editIntent.putExtra("isbn", book.getIsbn());
                                context.startActivity(editIntent);
                                return true;
                            case R.id.remove_book:
                                // Lógica para remover o livro
                                removeBook(book.getIsbn());
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
                return true;
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        TextView tvBookAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor); // Correspondente ao layout item_book.xml
        }
    }
}

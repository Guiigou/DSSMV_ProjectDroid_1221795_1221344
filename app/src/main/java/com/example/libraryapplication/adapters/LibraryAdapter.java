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
import com.example.libraryapplication.activities.BooksActivity;
import com.example.libraryapplication.activities.EditLibraryActivity;
import com.example.libraryapplication.activities.RemoveLibraryActivity;
import com.example.libraryapplication.models.Library;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {
    private Context context;
    private List<Library> librariesList;

    public LibraryAdapter(Context context, List<Library> librariesList) {
        this.context = context;
        this.librariesList = librariesList;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_library, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Library library = librariesList.get(position);
        holder.txtLibraryName.setText(library.getName());
        holder.txtLibraryAddress.setText(library.getAddress());

        // Formatar e exibir o horário de funcionamento
        String openTime = library.getOpenTime();
        String closeTime = library.getCloseTime();
        if (openTime != null && closeTime != null && !openTime.isEmpty() && !closeTime.isEmpty()) {
            String formattedHours = String.format("Aberto: %s - %s", openTime, closeTime);
            holder.txtLibraryHours.setText(formattedHours);
        } else {
            holder.txtLibraryHours.setText("Horário: Não disponível");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (library.getId() != null) {
                    Intent intent = new Intent(context, BooksActivity.class);
                    intent.putExtra("libraryId", library.getId());  // Passa o ID da biblioteca
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Erro: ID da biblioteca é nulo", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Long press: mostrar o popup de opções
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.drawer_menu); // Certifique-se de ter criado este arquivo XML com as opções de editar e remover

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.edit_library) {
                            // Lógica para editar a biblioteca
                            Intent editIntent = new Intent(context, EditLibraryActivity.class);
                            editIntent.putExtra("libraryId", library.getId());
                            editIntent.putExtra("libraryName", library.getName());
                            editIntent.putExtra("libraryAddress", library.getAddress());
                            editIntent.putExtra("openTime", library.getOpenTime());
                            editIntent.putExtra("closeTime", library.getCloseTime());
                            context.startActivity(editIntent);
                            return true;
                        } else if (itemId == R.id.remove_library) {
                            // Lógica para remover a biblioteca
                            Intent removeIntent = new Intent(context, RemoveLibraryActivity.class);
                            removeIntent.putExtra("libraryId", library.getId());
                            context.startActivity(removeIntent);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });


                popupMenu.show();
                return true; // Retorna true para indicar que o evento foi consumido
            }
        });
    }

    @Override
    public int getItemCount() {
        return librariesList.size();
    }

    public static class LibraryViewHolder extends RecyclerView.ViewHolder {
        TextView txtLibraryName;
        TextView txtLibraryAddress;
        TextView txtLibraryHours;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLibraryName = itemView.findViewById(R.id.txtLibraryName);
            txtLibraryAddress = itemView.findViewById(R.id.txtLibraryAddress);
            txtLibraryHours = itemView.findViewById(R.id.txtLibraryHours);
        }
    }
}

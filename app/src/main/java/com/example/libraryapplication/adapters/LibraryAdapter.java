package com.example.libraryapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
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

        // Formatar o horário de funcionamento diretamente das strings openTime e closeTime
        String openTime = library.getOpenTime();
        String closeTime = library.getCloseTime();

        if (openTime != null && closeTime != null && !openTime.isEmpty() && !closeTime.isEmpty()) {
            String formattedHours = String.format("Aberto: %s - %s", openTime, closeTime);
            holder.txtLibraryHours.setText(formattedHours);
        } else {
            holder.txtLibraryHours.setText("Horário: Não disponível");
        }
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

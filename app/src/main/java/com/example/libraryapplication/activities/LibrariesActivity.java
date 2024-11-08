package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.adapters.LibraryAdapter;
import com.example.libraryapplication.models.Library;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibrariesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewLibraries;
    private Button btnBackToMain;
    private List<Library> librariesList;
    private LibraryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        // Inicializar o botão de voltar para a MainActivity
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Fecha a activity atual, voltando para a MainActivity
            }
        });

        // Inicialize o menuButton
        ImageButton menuButton = findViewById(R.id.menuButton);

        // Configura o listener para abrir o menu pop-up
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v); // Chama a função para mostrar o menu pop-up
            }
        });

        // Inicializar o botão de filtro
        ImageButton filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterMenu(v);
            }
        });

        // Configurar RecyclerView
        recyclerViewLibraries = findViewById(R.id.recyclerViewLibraries);
        recyclerViewLibraries.setLayoutManager(new LinearLayoutManager(this));

        // Carregar as bibliotecas
        loadLibraries();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarregar a lista de bibliotecas sempre que a activity for retomada
        loadLibraries();
    }

    // Carregar a lista de bibliotecas da API
    private void loadLibraries() {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<List<Library>> call = libraryService.getLibraries();

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    librariesList = response.body();

                    if (librariesList.isEmpty()) {
                        Toast.makeText(LibrariesActivity.this, "Nenhuma biblioteca encontrada.", Toast.LENGTH_LONG).show();
                    } else {
                        adapter = new LibraryAdapter(LibrariesActivity.this, librariesList);
                        recyclerViewLibraries.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(LibrariesActivity.this, "Erro ao carregar bibliotecas. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                Toast.makeText(LibrariesActivity.this, "Erro ao carregar bibliotecas: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Mostrar o menu de opções
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.drawer_menu_library, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.add_library) {
                    startActivity(new Intent(LibrariesActivity.this, AddLibraryActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    // Mostrar o menu de filtro
    private void showFilterMenu(View view) {
        PopupMenu filterMenu = new PopupMenu(this, view);
        filterMenu.getMenuInflater().inflate(R.menu.filter_menu, filterMenu.getMenu());

        filterMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.filter_open) {
                    filterLibraries(true);
                    return true;
                } else if (itemId == R.id.filter_closed) {
                    filterLibraries(false);
                    return true;
                } else if (itemId == R.id.filter_all) {
                    adapter.updateLibraries(librariesList);
                    return true;
                } else {
                    return false;
                }
            }
        });

        filterMenu.show();
    }

    // Filtrar bibliotecas com base no horário de abertura/fechamento
    private void filterLibraries(boolean openNow) {
        List<Library> filteredList = new ArrayList<>();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        for (Library library : librariesList) {
            String openTime = library.getOpenTime();
            String closeTime = library.getCloseTime();

            if (openTime != null && closeTime != null && !openTime.isEmpty() && !closeTime.isEmpty()) {
                if (openNow) {
                    if (currentTime.compareTo(openTime) >= 0 && currentTime.compareTo(closeTime) <= 0) {
                        filteredList.add(library);
                    }
                } else {
                    if (currentTime.compareTo(openTime) < 0 || currentTime.compareTo(closeTime) > 0) {
                        filteredList.add(library);
                    }
                }
            }
        }

        adapter.updateLibraries(filteredList);
    }
}

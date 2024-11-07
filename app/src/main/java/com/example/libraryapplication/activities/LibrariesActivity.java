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

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.libraryapplication.R.*;

public class LibrariesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewLibraries;
    private Button btnBackToMain;
    private List<Library> librariesList;

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

        // Configurar RecyclerView
        recyclerViewLibraries = findViewById(R.id.recyclerViewLibraries);
        recyclerViewLibraries.setLayoutManager(new LinearLayoutManager(this));

        // Carregar as bibliotecas
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
                        LibraryAdapter adapter = new LibraryAdapter(LibrariesActivity.this, librariesList);
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

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.drawer_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.add_library) {
                    startActivity(new Intent(LibrariesActivity.this, AddLibraryActivity.class));
                    return true;
                } else if (itemId == R.id.edit_library) {
                    // Implementar a lógica para editar a biblioteca
                    return true;
                } else if (itemId == R.id.remove_library) {
                    startActivity(new Intent(LibrariesActivity.this, RemoveLibraryActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Adicione esta linha para mostrar o menu pop-up
        popupMenu.show();
    }

}
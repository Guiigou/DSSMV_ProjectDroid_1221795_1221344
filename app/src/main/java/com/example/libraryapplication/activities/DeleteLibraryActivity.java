package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.Library;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteLibraryActivity extends AppCompatActivity {

    private EditText edtLibraryLocalId;
    private Button btnDeleteLibrary;
    private List<Library> librariesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_library);

        edtLibraryLocalId = findViewById(R.id.edtTextLibraryId);
        btnDeleteLibrary = findViewById(R.id.btnDeleteLibrary);

        // Carrega a lista de bibliotecas ao iniciar a activity
        loadLibraries();

        btnDeleteLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localIdString = edtLibraryLocalId.getText().toString().trim();

                if (!localIdString.isEmpty()) {
                    int localId = Integer.parseInt(localIdString);  // Converte o input em int
                    deleteLibraryByLocalId(localId);
                } else {
                    Toast.makeText(DeleteLibraryActivity.this, "Insira um ID de biblioteca válido.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Carrega as bibliotecas para ter o mapeamento de localId para UUID
    private void loadLibraries() {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<List<Library>> call = libraryService.getLibraries();

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    librariesList = response.body();  // Guarda as bibliotecas na lista

                    // Atribui localIds para as bibliotecas
                    for (int i = 0; i < librariesList.size(); i++) {
                        librariesList.get(i).setLocalId(i + 1);
                    }
                } else {
                    Toast.makeText(DeleteLibraryActivity.this, "Erro ao carregar bibliotecas.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                Toast.makeText(DeleteLibraryActivity.this, "Erro ao carregar bibliotecas: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteLibraryByLocalId(int localId) {
        if (librariesList == null || librariesList.isEmpty()) {
            Toast.makeText(this, "Erro: Lista de bibliotecas não carregada.", Toast.LENGTH_LONG).show();
            return;
        }

        String uuid = findUUIDByLocalId(localId);

        if (uuid != null) {
            deleteLibrary(uuid);
        } else {
            Toast.makeText(this, "ID de biblioteca inválido.", Toast.LENGTH_LONG).show();
        }
    }

    // Mapeia o localId para o UUID
    private String findUUIDByLocalId(int localId) {
        for (Library library : librariesList) {
            if (library.getLocalId() == localId) {
                return library.getId();  // Retorna o UUID correspondente
            }
        }
        return null;  // Se não encontrar, retorna null
    }

    private void deleteLibrary(String uuid) {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.deleteLibrary(uuid);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DeleteLibraryActivity.this, "Biblioteca deletada com sucesso!", Toast.LENGTH_LONG).show();
                    finish();  // Fecha a activity após a exclusão
                } else {
                    Toast.makeText(DeleteLibraryActivity.this, "Erro ao deletar a biblioteca.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DeleteLibraryActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

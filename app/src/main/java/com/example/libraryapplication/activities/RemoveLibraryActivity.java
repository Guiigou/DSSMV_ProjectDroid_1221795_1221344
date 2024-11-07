package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recebe o ID da biblioteca do Intent
        String libraryId = getIntent().getStringExtra("libraryId");

        if (libraryId != null) {
            removeLibrary(libraryId);
        } else {
            Toast.makeText(this, "Erro: ID da biblioteca não foi fornecido.", Toast.LENGTH_LONG).show();
            finish(); // Fecha a atividade se o ID não for fornecido
        }
    }

    // Método para remover a biblioteca
    private void removeLibrary(String libraryId) {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.deleteLibrary(libraryId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RemoveLibraryActivity.this, "Biblioteca removida com sucesso.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RemoveLibraryActivity.this, "Erro ao remover biblioteca. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
                finish(); // Fecha a atividade após a remoção (sucesso ou falha)
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RemoveLibraryActivity.this, "Erro ao remover biblioteca: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish(); // Fecha a atividade após a falha
            }
        });
    }
}

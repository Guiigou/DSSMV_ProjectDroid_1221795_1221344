package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveLibraryActivity extends AppCompatActivity {

    private EditText edtLibraryIdToRemove;
    private Button btnRemoveLibrary;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_library);

        edtLibraryIdToRemove = findViewById(R.id.edtLibraryIdToRemove);
        btnRemoveLibrary = findViewById(R.id.btnRemoveLibrary);
        btnBack = findViewById(R.id.btnBack);

        // Configurar clique no botão de remover biblioteca
        btnRemoveLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String libraryId = edtLibraryIdToRemove.getText().toString().trim();

                if (!libraryId.isEmpty()) {
                    removeLibrary(libraryId);
                } else {
                    Toast.makeText(RemoveLibraryActivity.this, "Por favor, insira o ID da biblioteca a remover.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Configurar clique no botão de voltar
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Fecha a atividade atual e volta à anterior
            }
        });
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
                    edtLibraryIdToRemove.setText("");  // Limpa o campo de texto
                } else {
                    Toast.makeText(RemoveLibraryActivity.this, "Erro ao remover biblioteca. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RemoveLibraryActivity.this, "Erro ao remover biblioteca: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

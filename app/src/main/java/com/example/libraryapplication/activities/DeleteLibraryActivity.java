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

public class DeleteLibraryActivity extends AppCompatActivity {

    private EditText edtLibraryId;
    private Button btnDeleteLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_library);

        edtLibraryId = findViewById(R.id.edtTextLibraryId);
        btnDeleteLibrary = findViewById(R.id.btnDeleteLibrary);

        btnDeleteLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String libraryId = edtLibraryId.getText().toString().trim();

                if (!libraryId.isEmpty()) {
                    deleteLibrary(libraryId);
                } else {
                    Toast.makeText(DeleteLibraryActivity.this, "Insira um ID de biblioteca válido.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteLibrary(String libraryId) {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.deleteLibrary(libraryId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DeleteLibraryActivity.this, "Biblioteca deletada com sucesso!", Toast.LENGTH_LONG).show();
                    finish(); // Fecha a activity após a exclusão
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

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLibraryActivity extends AppCompatActivity {

    private EditText edtLibraryName;
    private EditText edtLibraryAddress;
    private EditText edtOpenTime;
    private EditText edtCloseTime;
    private Button btnSaveLibrary;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        edtLibraryName = findViewById(R.id.edtLibraryName);
        edtLibraryAddress = findViewById(R.id.edtLibraryAddress);
        edtOpenTime = findViewById(R.id.edtOpenTime);
        edtCloseTime = findViewById(R.id.edtCloseTime);
        btnSaveLibrary = findViewById(R.id.btnSaveLibrary);
        btnBack = findViewById(R.id.btnBack);

        // Configurar clique no botão de salvar biblioteca
        btnSaveLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String libraryName = edtLibraryName.getText().toString().trim();
                String libraryAddress = edtLibraryAddress.getText().toString().trim();
                String openTimeStr = edtOpenTime.getText().toString().trim();
                String closeTimeStr = edtCloseTime.getText().toString().trim();

                if (!libraryName.isEmpty() && !libraryAddress.isEmpty() && !openTimeStr.isEmpty() && !closeTimeStr.isEmpty()) {
                    try {
                        // Validando o formato da hora de abertura e fecho
                        if (!openTimeStr.matches("\\d{2}:\\d{2}") || !closeTimeStr.matches("\\d{2}:\\d{2}")) {
                            throw new IllegalArgumentException("Erro no formato das horas. Use HH:mm.");
                        }

                        // Criando a nova biblioteca
                        Library newLibrary = new Library();
                        newLibrary.setName(libraryName);
                        newLibrary.setAddress(libraryAddress);
                        newLibrary.setOpenTime(openTimeStr);  // Agora é uma String no formato esperado
                        newLibrary.setCloseTime(closeTimeStr);  // Agora é uma String no formato esperado

                        createLibrary(newLibrary);
                    } catch (Exception e) {
                        Toast.makeText(AddLibraryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddLibraryActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show();
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

    // Metodo para criar uma nova biblioteca através da API
    private void createLibrary(Library library) {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.createLibrary(library);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddLibraryActivity.this, "Biblioteca adicionada com sucesso.", Toast.LENGTH_LONG).show();
                    edtLibraryName.setText("");  // Limpar os campos de texto
                    edtLibraryAddress.setText("");
                    edtOpenTime.setText("");
                    edtCloseTime.setText("");
                } else {
                    Toast.makeText(AddLibraryActivity.this, "Erro ao adicionar biblioteca. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddLibraryActivity.this, "Erro ao adicionar biblioteca: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

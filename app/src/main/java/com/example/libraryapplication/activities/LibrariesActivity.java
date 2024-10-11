package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.Library;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibrariesActivity extends AppCompatActivity {
    private Button btnViewLibraries;
    private TextView txtLibraries;
    private List<Library> librariesList;  // Lista de bibliotecas recebidas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        btnViewLibraries = findViewById(R.id.btnViewLibraries);
        txtLibraries = findViewById(R.id.txtLibraries);

        // Ao clicar no botão, chama a função para fazer o GET das bibliotecas
        btnViewLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLibraries();  // Chama a função para obter as bibliotecas
            }
        });
    }

    private void getLibraries() {
        // Faz a chamada GET à API usando Retrofit
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<List<Library>> call = libraryService.getLibraries();

        call.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    librariesList = response.body();  // Guarda as bibliotecas na lista
                    StringBuilder builder = new StringBuilder();

                    // Atribui IDs locais para exibição
                    for (int i = 0; i < librariesList.size(); i++) {
                        Library library = librariesList.get(i);
                        library.setLocalId(i + 1);  // Atribui um ID local (sequencial)

                        // Exibe as bibliotecas com os IDs locais
                        builder.append("Local ID: ").append(library.getLocalId()).append("\n");
                        builder.append("Nome: ").append(library.getName()).append("\n");
                        builder.append("Endereço: ").append(library.getAddress()).append("\n");
                        builder.append("Aberto: ").append(library.isOpen() ? "Sim" : "Não").append("\n");
                        builder.append("Hora de Abertura: ").append(library.getOpenTime()).append("\n");
                        builder.append("Hora de Fecho: ").append(library.getCloseTime()).append("\n");
                        builder.append("-----------------------------------\n");
                    }
                    txtLibraries.setText(builder.toString());  // Atualiza o TextView com a lista de bibliotecas
                } else {
                    txtLibraries.setText("Erro ao obter as bibliotecas.");
                }
            }

            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                txtLibraries.setText("Erro ao obter as bibliotecas: " + t.getMessage());
            }
        });
    }
}

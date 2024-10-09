package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.adapters.LibraryAdapter;
import com.example.libraryapplication.api.ApiClient;
import com.example.libraryapplication.api.LibraryAPI;
import com.example.libraryapplication.dto.LibraryDTO;
import com.example.libraryapplication.mapper.Mapper;
import com.example.libraryapplication.models.Library;
import java.util.List;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView libraryRecyclerView;
    private LibraryAdapter libraryAdapter;
    private Button loadLibrariesButton;
    private static final String BASE_URL = "http://193.136.62.24/";  // Ajusta para a tua API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar RecyclerView
        libraryRecyclerView = findViewById(R.id.libraryRecyclerView);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar o botão
        loadLibrariesButton = findViewById(R.id.loadLibrariesButton);

        // Configurar o clique do botão
        loadLibrariesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configurar a API
                LibraryAPI apiService = ApiClient.getClient(BASE_URL).create(LibraryAPI.class);

                // Fazer a chamada GET para obter as bibliotecas
                apiService.getLibraries().enqueue(new Callback<List<LibraryDTO>>() {
                    @Override
                    public void onResponse(Call<List<LibraryDTO>> call, Response<List<LibraryDTO>> response) {
                        if (response.isSuccessful()) {
                            List<LibraryDTO> libraryDTOs = response.body();

                            // Mapear os DTOs para o modelo de domínio Library
                            List<Library> libraries = new ArrayList<>();
                            for (LibraryDTO dto : libraryDTOs) {
                                libraries.add(Mapper.dtoToModel(dto));
                            }

                            // Configurar o Adapter com a lista de bibliotecas
                            libraryAdapter = new LibraryAdapter(libraries);
                            libraryRecyclerView.setAdapter(libraryAdapter);
                        } else {
                            Log.e("API_ERROR", "Erro: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LibraryDTO>> call, Throwable t) {
                        Log.e("API_ERROR", "Falha na requisição: " + t.getMessage());
                    }
                });
            }
        });
    }
}

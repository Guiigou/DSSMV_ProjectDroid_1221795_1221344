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
import com.example.libraryapplication.models.Library;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView libraryRecyclerView;
    private LibraryAdapter libraryAdapter;
    private Button loadLibrariesButton;

    private static final String BASE_URL = "http://193.136.62.24/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar RecyclerView
        libraryRecyclerView = findViewById(R.id.libraryRecyclerView);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar o botão
        loadLibrariesButton = findViewById(R.id.loadLibrariesButton);

        // Configura o clique do botão
        loadLibrariesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configura a API
                LibraryAPI apiService = ApiClient.getClient(BASE_URL).create(LibraryAPI.class);

                // Faz a chamada GET para obter as bibliotecas
                apiService.getLibraries().enqueue(new Callback<List<Library>>() {
                    @Override
                    public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                        if (response.isSuccessful()) {
                            List<Library> libraries = response.body();
                            Log.d("MainActivity", "Number of libraries: " + libraries.size());
                            // Configura o Adapter com a lista de bibliotecas
                            libraryAdapter = new LibraryAdapter(libraries);
                            libraryRecyclerView.setAdapter(libraryAdapter);
                        } else {
                            Log.e("API_ERROR", "Erro: " + response.code());
                        }
                    }



                    @Override
                    public void onFailure(Call<List<Library>> call, Throwable t) {
                        Log.e("API_ERROR", "Falha na requisição: " + t.getMessage());
                    }
                });
            }
        });
    }
}

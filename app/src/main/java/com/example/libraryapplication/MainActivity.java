package com.example.libraryapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.libraryapplication.Library;
import com.example.libraryapplication.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://your-api-url.com/";  // Coloque a URL base da API aqui

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuração do Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Para converter JSON
                .build();

        // Criar a instância da API
        api libraryApi = retrofit.create(api.class);

        // Fazer a chamada GET
        libraryApi.getLibraries().enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful()) {
                    // Sucesso, pegar os dados retornados
                    List<Library> libraries = response.body();
                    // Aqui você pode fazer algo com os dados, como exibir no log
                    for (Library library : libraries) {
                        Log.d("Library", "Name: " + library.getName() + ", Address: " + library.getAddress());
                    }
                } else {
                    // Tratar erros da API
                    Log.e("API_ERROR", "Erro: " + response.code());
                }
            }


            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                // Tratar falhas de requisição, como problemas de conexão
                Log.e("API_ERROR", "Falha na requisição: " + t.getMessage());
            }
        });
    }
}

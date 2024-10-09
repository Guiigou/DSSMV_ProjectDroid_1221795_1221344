package com.example.libraryapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.Library;
import com.example.libraryapplication.models.LocalTime;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLibraryActivity extends AppCompatActivity {
    private EditText edtName, edtAddress, edtOpenDays, edtOpenHour, edtOpenMinute, edtCloseHour, edtCloseMinute;
    private Button btnAddLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        // Referências aos campos de entrada
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtOpenDays = findViewById(R.id.edtOpenDays);
        edtOpenHour = findViewById(R.id.edtOpenHour);
        edtOpenMinute = findViewById(R.id.edtOpenMinute);
        edtCloseHour = findViewById(R.id.edtCloseHour);
        edtCloseMinute = findViewById(R.id.edtCloseMinute);
        btnAddLibrary = findViewById(R.id.btnAddLibrary);

        btnAddLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria uma nova Library com os dados inseridos
                Library library = new Library();
                library.setName(edtName.getText().toString());
                library.setAddress(edtAddress.getText().toString());
                library.setOpenDays(edtOpenDays.getText().toString());

                String openTime = String.format("%02d:%02d:00", Integer.parseInt(edtOpenHour.getText().toString()), Integer.parseInt(edtOpenMinute.getText().toString()));
                String closeTime = String.format("%02d:%02d:00", Integer.parseInt(edtCloseHour.getText().toString()), Integer.parseInt(edtCloseMinute.getText().toString()));

                library.setOpenTime(openTime);
                library.setCloseTime(closeTime);


                // Chama a função para enviar os dados da biblioteca
                postLibrary(library);
            }
        });
    }

    private void postLibrary(Library library) {
        // Faz a chamada POST à API usando Retrofit
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.createLibrary(library);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddLibraryActivity.this, "Biblioteca criada com sucesso!", Toast.LENGTH_LONG).show();
                    finish();  // Fecha a Activity
                } else {
                    Toast.makeText(AddLibraryActivity.this, "Erro ao criar a biblioteca.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddLibraryActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

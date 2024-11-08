package com.example.libraryapplication.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;
import com.example.libraryapplication.models.Library;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryService;

import java.util.Locale;

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

        // Abrir TimePickerDialog ao clicar no campo de hora de abertura
        edtOpenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(edtOpenTime);
            }
        });

        // Abrir TimePickerDialog ao clicar no campo de hora de fecho
        edtCloseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(edtCloseTime);
            }
        });

        // Configurar clique no botão de salvar biblioteca
        btnSaveLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String libraryName = edtLibraryName.getText().toString().trim();
                String libraryAddress = edtLibraryAddress.getText().toString().trim();
                String openTimeStr = edtOpenTime.getText().toString().trim();
                String closeTimeStr = edtCloseTime.getText().toString().trim();

                if (!libraryName.isEmpty() && !libraryAddress.isEmpty() && !openTimeStr.isEmpty() && !closeTimeStr.isEmpty()) {
                    Library newLibrary = new Library();
                    newLibrary.setName(libraryName);
                    newLibrary.setAddress(libraryAddress);
                    newLibrary.setOpenTime(openTimeStr);
                    newLibrary.setCloseTime(closeTimeStr);

                    createLibrary(newLibrary);
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

    // Método para exibir TimePickerDialog e definir o horário selecionado no EditText correspondente
    private void showTimePickerDialog(EditText editText) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        editText.setText(formattedTime);
                    }
                }, 0, 0, true); // Definir o valor inicial como 00:00 e usar o formato 24 horas
        timePickerDialog.show();
    }

    // Método para criar uma nova biblioteca através da API
    private void createLibrary(Library library) {
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.createLibrary(library);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddLibraryActivity.this, "Biblioteca adicionada com sucesso.", Toast.LENGTH_LONG).show();
                    edtLibraryName.setText("");
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

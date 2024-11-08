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

public class EditLibraryActivity extends AppCompatActivity {

    private EditText edtLibraryName;
    private EditText edtLibraryAddress;
    private EditText edtOpenTime;
    private EditText edtCloseTime;
    private Button btnSaveChanges;
    private Button btnBack;

    private String libraryId; // ID da biblioteca a ser editada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_library);

        // Inicialize os campos de texto
        edtLibraryName = findViewById(R.id.edtLibraryName);
        edtLibraryAddress = findViewById(R.id.edtLibraryAddress);
        edtOpenTime = findViewById(R.id.edtOpenTime);
        edtCloseTime = findViewById(R.id.edtCloseTime);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnBack = findViewById(R.id.btnBack);

        // Obter os dados do Intent
        libraryId = getIntent().getStringExtra("libraryId");
        if (libraryId == null) {
            Toast.makeText(this, "Erro: ID da biblioteca é nulo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        String libraryName = getIntent().getStringExtra("libraryName");
        String libraryAddress = getIntent().getStringExtra("libraryAddress");
        String openTime = getIntent().getStringExtra("openTime");
        String closeTime = getIntent().getStringExtra("closeTime");

        // Preencher os campos com os dados existentes
        edtLibraryName.setText(libraryName);
        edtLibraryAddress.setText(libraryAddress);
        edtOpenTime.setText(openTime);
        edtCloseTime.setText(closeTime);

        // Configurar para abrir o TimePickerDialog ao clicar nos campos de hora
        edtOpenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(edtOpenTime);
            }
        });

        edtCloseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(edtCloseTime);
            }
        });

        // Configurar o botão de salvar mudanças
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLibrary();
            }
        });

        // Configurar o botão de voltar
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha a atividade e volta para a anterior
            }
        });
    }

    private void updateLibrary() {
        String name = edtLibraryName.getText().toString().trim();
        String address = edtLibraryAddress.getText().toString().trim();
        String openTime = edtOpenTime.getText().toString().trim();
        String closeTime = edtCloseTime.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || openTime.isEmpty() || closeTime.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show();
            return;
        }

        // Cria um objeto Library com os novos dados
        Library updatedLibrary = new Library();
        updatedLibrary.setId(libraryId); // Mantenha o ID original
        updatedLibrary.setName(name);
        updatedLibrary.setAddress(address);
        updatedLibrary.setOpenTime(openTime);
        updatedLibrary.setCloseTime(closeTime);

        // Envia a atualização para o servidor usando a API
        LibraryService libraryService = ApiClient.getClient().create(LibraryService.class);
        Call<Void> call = libraryService.updateLibrary(libraryId, updatedLibrary);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditLibraryActivity.this, "Biblioteca atualizada com sucesso.", Toast.LENGTH_LONG).show();
                    finish(); // Fecha a atividade após a atualização bem-sucedida
                } else {
                    Toast.makeText(EditLibraryActivity.this, "Erro ao atualizar a biblioteca. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditLibraryActivity.this, "Erro ao atualizar a biblioteca: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Metodo para exibir o TimePickerDialog e definir o horário selecionado no EditText correspondente
    private void showTimePickerDialog(final EditText editText) {
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
}

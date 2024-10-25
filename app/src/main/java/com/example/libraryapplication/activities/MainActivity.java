package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;

public class MainActivity extends AppCompatActivity {

    private Button btnViewLibraries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando o botão
        btnViewLibraries = findViewById(R.id.btnViewLibraries);

        // Configurando o clique do botão para abrir a lista de bibliotecas
        btnViewLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibrariesActivity.class);
                startActivity(intent);
            }
        });
    }
}

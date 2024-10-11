package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;

public class LibraryMenuActivity extends AppCompatActivity {
    private Button btnViewLibraries;
    private Button btnAddLibrary;
    private Button btnDeleteLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_menu);

        btnViewLibraries = findViewById(R.id.btnViewLibraries);
        btnAddLibrary = findViewById(R.id.btnAddLibrary);
        btnDeleteLibrary = findViewById(R.id.btnDeleteLibrary);

        btnViewLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireciona para a LibrariesActivity
                Intent intent = new Intent(LibraryMenuActivity.this, LibrariesActivity.class);
                startActivity(intent);
            }
        });

        btnAddLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryMenuActivity.this, AddLibraryActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryMenuActivity.this, DeleteLibraryActivity.class);
                startActivity(intent);
            }
        });
    }
}

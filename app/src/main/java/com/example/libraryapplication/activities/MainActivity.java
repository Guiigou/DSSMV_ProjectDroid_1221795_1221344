package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;

public class MainActivity extends AppCompatActivity {
    private Button btnLibraries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLibraries = findViewById(R.id.btnLibraries);
        btnLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireciona para a LibrariesActivity
                Intent intent = new Intent(MainActivity.this, LibrariesActivity.class);
                startActivity(intent);
            }
        });
    }
}

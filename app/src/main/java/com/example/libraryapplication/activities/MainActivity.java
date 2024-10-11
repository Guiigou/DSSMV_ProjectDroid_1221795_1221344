package com.example.libraryapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.libraryapplication.R;

public class MainActivity extends AppCompatActivity {
    private Button btnLibraryMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLibraryMenu = findViewById(R.id.btnLibraryMenu);

        btnLibraryMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireciona para a LibraryMenuActivity
                Intent intent = new Intent(MainActivity.this, LibraryMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}

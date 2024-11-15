package com.example.libraryapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.adapters.BookAdapter;
import com.example.libraryapplication.models.LibraryBook;
import com.example.libraryapplication.services.ApiClient;
import com.example.libraryapplication.services.LibraryBookService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class BooksActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private String libraryId;
    private String libraryName;

    // Variáveis para detecção de movimento
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 20.0f; // Valor do limiar para detectar "shake"
    private static final long SHAKE_TIME_LAPSE = 2000; // 2 segundos entre "shakes"
    private long lastShakeTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // Inicializar RecyclerView e TextView
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        TextView tvLibraryName = findViewById(R.id.tvLibraryName);
        FloatingActionButton fabAddBook = findViewById(R.id.fabAddBook);

        // Receber os dados do Intent
        libraryId = getIntent().getStringExtra("libraryId");
        libraryName = getIntent().getStringExtra("libraryName");

        Log.d("BooksActivity", "Library ID recebido: " + libraryId);
        Log.d("BooksActivity", "Library Name recebido: " + libraryName);

        if (libraryId == null) {
            Toast.makeText(this, "Erro: ID da biblioteca não foi passado.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Definir o nome da biblioteca na TextView
        if (libraryName != null) {
            tvLibraryName.setText(libraryName);
        }

        // Inicializar SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        // Configurar o FloatingActionButton para adicionar livro
        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BooksActivity.this, AddBookActivity.class);
                intent.putExtra("libraryId", libraryId);
                startActivityForResult(intent, 1); // Código para identificar a Activity
            }
        });

        // Carregar os livros da biblioteca
        loadBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar o listener do acelerômetro quando a activity é retomada
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        // Recarregar a lista de livros sempre que a activity for retomada
        loadBooks();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar o listener do sensor quando a activity é pausada
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void loadBooks() {
        LibraryBookService libraryBookService = ApiClient.getClient().create(LibraryBookService.class);
        Call<List<LibraryBook>> call = libraryBookService.getBooks(libraryId);

        call.enqueue(new Callback<List<LibraryBook>>() {
            @Override
            public void onResponse(Call<List<LibraryBook>> call, Response<List<LibraryBook>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LibraryBook> books = response.body();
                    if (books.isEmpty()) {
                        Toast.makeText(BooksActivity.this, "Nenhum livro encontrado.", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("BooksActivity", "Número de livros recebidos: " + books.size());
                        bookAdapter = new BookAdapter(BooksActivity.this, books);
                        recyclerViewBooks.setAdapter(bookAdapter);
                    }
                } else {
                    Toast.makeText(BooksActivity.this, "Erro ao carregar livros. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LibraryBook>> call, Throwable t) {
                Toast.makeText(BooksActivity.this, "Erro ao carregar livros: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

            long currentTime = System.currentTimeMillis();
            if (acceleration > SHAKE_THRESHOLD) {
                if ((currentTime - lastShakeTime) > SHAKE_TIME_LAPSE) {
                    lastShakeTime = currentTime;
                    Toast.makeText(this, "You are in the library: " + libraryName, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não é necessário implementar aqui
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadBooks(); // Recarrega a lista de livros após adicionar um novo
        }
    }
}

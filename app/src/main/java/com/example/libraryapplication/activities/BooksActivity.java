package com.example.libraryapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private String libraryId;
    private String libraryName;
    private List<LibraryBook> booksList;

    // Variáveis para detecção de movimento
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 20.0f; // Valor do limiar para detectar "shake"
    private static final long SHAKE_TIME_LAPSE = 2000; // 2 segundos entre "shakes"
    private long lastShakeTime = 0;

    // Código para reconhecimento de voz
    private static final int VOICE_SEARCH_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        // Inicializar RecyclerView e TextView
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        TextView tvLibraryName = findViewById(R.id.tvLibraryName);
        FloatingActionButton fabVoiceSearch = findViewById(R.id.fabVoiceSearch);

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

        // Configurar o botão de pesquisa por voz
        fabVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognition();
            }
        });

        // Carregar os livros da biblioteca
        loadBooks();
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);  // Define o idioma para inglês
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the book title you are looking for");
        startActivityForResult(intent, VOICE_SEARCH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_SEARCH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String searchQuery = matches.get(0);
                searchBookByVoice(searchQuery);
            }
        }
    }

    private void searchBookByVoice(String bookTitle) {
        for (LibraryBook book : booksList) {
            if (book.getBookTitle().equalsIgnoreCase(bookTitle)) {
                // Abrir a tela de detalhes do livro se encontrado
                Intent intent = new Intent(BooksActivity.this, BookDetailActivity.class);
                intent.putExtra("title", book.getBookTitle());
                intent.putExtra("authors", book.getBookAuthors());
                intent.putExtra("description", book.getBookDescription());
                intent.putExtra("stock", book.getStock());

                // Adicionando a URL da capa ao Intent
                try {
                    String isbn = book.getIsbn().replaceAll("-", "");
                    String encodedIsbn = URLEncoder.encode(isbn, "UTF-8");
                    String coverUrl = "http://193.136.62.24/v1/assets/cover/" + encodedIsbn + "-M.jpg";
                    intent.putExtra("coverUrl", coverUrl);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Book not found: " + bookTitle, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        loadBooks();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
                    booksList = response.body();
                    if (booksList.isEmpty()) {
                        Toast.makeText(BooksActivity.this, "Nenhum livro encontrado.", Toast.LENGTH_LONG).show();
                    } else {
                        bookAdapter = new BookAdapter(BooksActivity.this, booksList);
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
}

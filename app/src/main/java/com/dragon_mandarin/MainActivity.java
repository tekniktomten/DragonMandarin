package com.dragon_mandarin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton dictionaryButton;

    private FloatingActionButton hanziButton;

    private FloatingActionButton flashcardsButton;

    private FloatingActionButton ocrButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up buttons
        dictionaryButton = findViewById(R.id.dictionary);
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                startActivity(intent);
                overridePendingTransition( 0, R.anim.my_splash_fade_out );
            }
        });
        hanziButton = findViewById(R.id.hanzi);
        hanziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseHanziActivity.class);
                startActivity(intent);
                overridePendingTransition( 0, R.anim.my_splash_fade_out );
            }
        });
        flashcardsButton = findViewById(R.id.flashcards);
        flashcardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseFlashcardsActivity.class);
                startActivity(intent);
                overridePendingTransition( 0, R.anim.my_splash_fade_out );
            }
        });
        ocrButton = findViewById(R.id.ocr);
        ocrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OCRActivity.class);
                startActivity(intent);
                overridePendingTransition( 0, R.anim.my_splash_fade_out );
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem changeHanzi = menu.findItem(R.id.changeHanzi);
        if (Utility.simplified) changeHanzi.setTitle("Change to traditional");
        else changeHanzi.setTitle("Change to simplified");
        return super.onPrepareOptionsMenu(menu);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem changeHanzi = menu.findItem(R.id.settings);
        if (Utility.simplified) changeHanzi.setTitle("Change to traditional");
        else changeHanzi.setTitle("Change to simplified");
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                break;
            case R.id.changeHanzi:
                Utility.simplified = !Utility.simplified;
                if (Utility.simplified) item.setTitle("Change to traditional");
                else item.setTitle("Change to simplified");
                break;
            case R.id.licence:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

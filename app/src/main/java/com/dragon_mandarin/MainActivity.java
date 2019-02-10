package com.dragon_mandarin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}

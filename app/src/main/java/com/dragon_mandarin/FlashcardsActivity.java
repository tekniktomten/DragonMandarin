package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FlashcardsActivity extends AppCompatActivity {

    String chosen_flashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        Intent intent = getIntent();
        chosen_flashcards = intent.getStringExtra("chosen flashcards");
    }
}
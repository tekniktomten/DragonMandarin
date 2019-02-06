package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordActivity extends AppCompatActivity {

    private Word word;

    private TextView hanzi;

    private TextView pinyin;

    private TextView tranlations;

    private TextView measureWord;

    private Button hanziButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Intent intent = getIntent();
        word = (Word) intent.getSerializableExtra("word");

        hanzi = findViewById(R.id.wordHanzi);
        hanzi.setText(word.getHanzi());
        pinyin = findViewById(R.id.wordPinyin);
        pinyin.setText(word.getPinyin());
        tranlations = findViewById(R.id.wordTranslations);
        tranlations.setText("Tranlations:" + word.getTranslations());
        measureWord = findViewById(R.id.wordMeasureWord);
        measureWord.setText("Measure Word:" + word.getMeasureWord());
        hanziButton = findViewById(R.id.wordHanziButton);
        hanziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordActivity.this, HanziActivity.class);
                intent.putExtra("word", word);
                startActivity(intent);
            }
        });
    }
}

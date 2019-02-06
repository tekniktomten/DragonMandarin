package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WordActivity extends AppCompatActivity {

    private Word word;

    private boolean favorite = false;

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

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wordmenu, menu);
        MenuItem heart = menu.findItem(R.id.heart);
        ArrayList<Word> favorites = Word.readFromFavoritesFile(this);
        if (favorites != null) {
            for (Word w : favorites) {
                if (w.getHanzi().equals(word.getHanzi())) {
                    favorite = true;
                    heart.setIcon(R.drawable.ic_favorite_blue_24dp);
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.heart) {
            if (!favorite) {
                word.saveToFavoritesFile(this);
                favorite = true;
                item.setIcon(R.drawable.ic_favorite_blue_24dp);
            }
            else {
                word.removeFromFavoritesFile(this);
                favorite = false;
                item.setIcon(R.drawable.ic_favorite_border_black_24dp);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

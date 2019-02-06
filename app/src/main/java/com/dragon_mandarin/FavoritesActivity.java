package com.dragon_mandarin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ListView listView;

    private WordListAdapter list;

    ArrayList<Word> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favorites = Word.readFromFavoritesFile(this);
        if (favorites != null && favorites.size() > 0) {
            list = new WordListAdapter(this, favorites);
            listView = findViewById(R.id.favorites);
            listView.setAdapter(list);
        } else Toast.makeText(this, "You don't have any favorites yet!", Toast.LENGTH_LONG).show();
    }

}

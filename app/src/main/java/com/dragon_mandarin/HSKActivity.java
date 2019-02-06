package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class HSKActivity extends AppCompatActivity {

    private ListView listView;

    private EditText search;

    private WordListAdapter list;

    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsk);

        Intent intent = getIntent();
        number = intent.getIntExtra("hsk", 0);
        if (number == 0) list = new WordListAdapter(this, Utility.getAllHskList(this));
        else list = new WordListAdapter(this, Utility.getHskList(number, this));
        listView = findViewById(R.id.wordList);
        listView.setAdapter(list);
        search = findViewById(R.id.searchDictionary);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

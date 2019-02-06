package com.dragon_mandarin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CEActivity extends AppCompatActivity {

    private ListView listView;

    private EditText search;

    private TextView textView;

    private WordListAdapter list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce);

        list = new WordListAdapter(this, Utility.getCEList(this));
        listView = findViewById(R.id.wordCE);
        listView.setAdapter(list);
        search = findViewById(R.id.searchCE);
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
        textView = findViewById(R.id.numberOfWordsCE);
        textView.setText(Integer.toString(list.getNumberOfWords())); // TODO should get updated
    }
}

package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private ListView listView;

    ArrayList<String> list = new ArrayList<String>(Arrays.asList("HSK 1", "HSK 2", "HSK 3", "HSK 4", "HSK 5", "HSK 6", "Dictionary"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        listView = findViewById(R.id.dictList);
        List<String> array_list = list;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                array_list );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 6) {
                    Intent intent = new Intent(DictionaryActivity.this, HSKActivity.class);
                    intent.putExtra("hsk", position + 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DictionaryActivity.this, CEActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

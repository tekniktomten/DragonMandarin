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

public class ChooseHanziActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<String> hanzi = new ArrayList<String>(Arrays.asList("HSK 1", "HSK 2", "HSK 3", "HSK 4", "HSK 5", "HSK 6", "HSK 1-6", "Favorites", "Difficult"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hanzi);

        listView = findViewById(R.id.chooseHanziList);
        List<String> your_array_list = hanzi;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseHanziActivity.this, HanziActivity.class);
                intent.putExtra("chooseHanziItem", position + 1);
                startActivity(intent);
            }
        });
    }
}

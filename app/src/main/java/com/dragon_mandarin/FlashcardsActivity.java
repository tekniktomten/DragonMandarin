package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class FlashcardsActivity extends AppCompatActivity {

    int chosen_flashcards;
    private CardView cardView;
    private TextView info_text;
    private Button incorrectButton;
    private Button flipButton;
    private Button correctButton;
    private ArrayList<Word> words;
    private ArrayList<Word> scrambled_words;
    private int index = -1;
    private boolean flipped = false;
    private boolean isExiting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        Intent intent = getIntent();
        chosen_flashcards = intent.getIntExtra("chosen flashcards", 0);
        words = Utility.getHskList(chosen_flashcards, this);
        scrambled_words = new ArrayList<Word>(words);
        Collections.shuffle(scrambled_words);
        cardView = findViewById(R.id.card_view);
        info_text = findViewById(R.id.info_text);
        incorrectButton = findViewById(R.id.flashcardsIncorrectButton);
        flipButton = findViewById(R.id.flashcardsFlipButton);
        correctButton= findViewById(R.id.flashcardsCorrectButton);

        incorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.newHardWord(scrambled_words.get(index));
                loadNextCard();
            }
        });

        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExiting) {
                    scrambled_words.remove(scrambled_words.get(index)); // TODO CRASHES IF PRESSED MANY TIMES BEFORE CLOSING
                    loadNextCard();
                }
            }
        });


        loadNextCard();
    }

    private void loadCard(Word w) { // TODO MAYBE CHANGE ARGUMENT
        info_text.setText(w.getHanzi());
        info_text.setTextSize(64);
        flipped = false;
    }

    private void flipCard() {
        if (!flipped) {
            info_text.setText(scrambled_words.get(index).getPinyin() + "\n" + scrambled_words.get(index).getTranslations());
            info_text.setTextSize(20);
            flipped = true;
        }
        else {
            loadCard(scrambled_words.get(index));
        }
    }

    private void loadNextCard() {
        try{
            loadCard(scrambled_words.get(++index));
        }
        catch (IndexOutOfBoundsException e) {
            if (scrambled_words.size() == 0) {
                isExiting = true;

                Toast.makeText(this, "Flashcards Completed!", Toast.LENGTH_LONG).show();

                final Intent intent = new Intent(this, MainActivity.class);

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000); // As I am using LENGTH_LONG in Toast
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
            }
            else {
                Collections.shuffle(scrambled_words);
                index = -1;
                loadCard(scrambled_words.get(++index));
            }
        }
    }
}
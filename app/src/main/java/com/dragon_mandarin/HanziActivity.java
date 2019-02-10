package com.dragon_mandarin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HanziActivity extends AppCompatActivity {

    private Word word;

    private char[] hanzi_array;

    private String hanzi;

    private int hanzi_index = -1;

    private int word_index = -1;

    private TextView textView;

    private WebView webView;

    private Button quizButton;

    private Button hintButton;

    private Button showButton;

    private Button animateButton;

    private Button hardButton;

    private Button easyButton;

    private Button nextButton;

    private ArrayList<Word> words;

    private int chooseHanziItem;
    private boolean intentIsChooseHanziItem = false;
    private boolean intentIsWordList = false;
    private boolean intentIsWord = false;

    private WebAppInterface webAppInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanzi);

        Intent intent = getIntent();
        chooseHanziItem = intent.getIntExtra("chooseHanziItem", 0);
        if (chooseHanziItem != 0) intentIsChooseHanziItem = true;
        ArrayList<Word> chosenWordList = (ArrayList<Word>) intent.getSerializableExtra("wordList");
        if (chosenWordList != null) intentIsWordList = true;
        Word chosenWord = (Word) intent.getSerializableExtra("word");
        if (chosenWord != null) intentIsWord = true;

        if (intentIsChooseHanziItem) {
            words = null;
            if (chooseHanziItem == 7) words = Utility.getAllHskList(this);
            else if (chooseHanziItem < 7) words = Utility.getHskList(chooseHanziItem, this);
            else if (chooseHanziItem == 8) {
                words = Word.readFromFavoritesFile(this);
            } else if (chooseHanziItem == 9) {
                words = Utility.gethardWords();
            } else {
                // TODO
            }
        } else if (intentIsWordList) {

        } else if (intentIsWord) {
            words = new ArrayList<Word>();
            words.add(chosenWord);
        } else {
            // TODO
        }

        hanzi_array = words.get(++word_index).getHanzi().toCharArray();
        hanzi = "" + hanzi_array[++hanzi_index];

        textView = findViewById(R.id.hanziTitle);
        textView.setText(words.get(word_index).getPinyin());
        webView = findViewById(R.id.hanziWeb);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/demo/index.html");
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                webView.loadUrl("javascript:init('" + hanzi + "')");
                if (false) { // TODO fix condition
                    webView.loadUrl("javascript:quiz_init()");
                }
            }
        });
        webAppInterface = new WebAppInterface(this);
        webView.addJavascriptInterface(webAppInterface, "Android");
        quizButton = findViewById(R.id.hanziQuizButton);
        quizButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                quiz();
            }
        });
        hintButton = findViewById(R.id.hanziHintButton);
        hintButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                toggle_hint();
            }
        });
        showButton = findViewById(R.id.hanziShowButton);
        showButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if (!webAppInterface.quizIsUndergoing) toggle_show();
            }
        });
        animateButton = findViewById(R.id.hanziAnimateButton);
        animateButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
        nextButton = findViewById(R.id.hanziNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        hardButton = findViewById(R.id.hanziHard);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextHanzi();
            }
        });
        easyButton = findViewById(R.id.hanziEasy);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.removeHardWord(word);
                nextHanzi();
            }
        });
    }



    private void nextHanzi() {
        try {
            hanzi = "" + hanzi_array[++hanzi_index];
            init();

        } catch (IndexOutOfBoundsException e) {
            hanzi_index = -1;

            if (intentIsWordList) {

            }

            if (intentIsChooseHanziItem) {
                if (chooseHanziItem == 0) {
                    // TODO
                }
                else if (chooseHanziItem < 9) {
                    try {
                        hanzi_array = words.get(++word_index).getHanzi().toCharArray();
                        hanzi = "" + hanzi_array[++hanzi_index];
                        init();
                    } catch (IndexOutOfBoundsException i) {
                        onBackPressed();
                    }
                }
                else if (chooseHanziItem == 9) { // TODO bad idea to hard code
                    word = Utility.getHardWord();
                    hanzi_array = word.getHanzi().toCharArray();
                    hanzi = "" + hanzi_array[++hanzi_index];

                    init();
                }
            }
        }
    }

    private void toggle_show() {
        webView.loadUrl("javascript:toggle('" + hanzi + "')");
    }

    private void toggle_hint() {
        webView.loadUrl("javascript:toggle_hint('" + hanzi + "')");
    }

    private void animate() {
        webView.loadUrl("javascript:animate('" + hanzi + "')");
    }

    private void quiz() {
        webView.loadUrl("javascript:quiz('" + hanzi + "')");
    }

    private void init() {
        webView.loadUrl("javascript:init('" + hanzi + "')");
        textView.setText(words.get(word_index).getPinyin());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, R.anim.my_splash_fade_out );
    }

    public class WebAppInterface {
        Context mContext;

        int numberOfMistakes;
        int numberOfTries;
        int numberOfStrokes;

        boolean quizIsUndergoing;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void newQuiz() {
            numberOfMistakes = 0;
            numberOfTries = 0;
            numberOfStrokes = 0;
            quizIsUndergoing = true;
        }

        @JavascriptInterface
        public void incorrectStroke() {
            if (++numberOfTries == 3) numberOfMistakes++;
        }

        @JavascriptInterface
        public void correctStroke() {
            numberOfTries = 0;
            ++numberOfStrokes;
        }

        @JavascriptInterface
        public void quizCompleted() {
            quizIsUndergoing = false;
            Toast toast = Toast.makeText(mContext, "You knew " + Integer.toString(numberOfStrokes - numberOfMistakes) + " out of " + Integer.toString(numberOfStrokes) + " strokes!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 100);
            toast.show();
        }

    }
}

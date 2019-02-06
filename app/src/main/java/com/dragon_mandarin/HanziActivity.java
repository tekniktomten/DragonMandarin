package com.dragon_mandarin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class HanziActivity extends AppCompatActivity {

    private Word word;

    private char[] hanzi_array;

    private String hanzi;

    private int hanzi_index = -1;

    private WebView webView;

    private Button quizButton;

    private Button hintButton;

    private Button showButton;

    private Button animateButton;

    private Button hardButton;

    private Button easyButton;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanzi);

        Intent intent = getIntent();
        try {
            word = (Word) intent.getSerializableExtra("word");
            hanzi_array = word.getHanzi().toCharArray();
        } catch (NullPointerException e) {
            word = Utility.getHardWord();
            hanzi_array = word.getHanzi().toCharArray();
        }
        hanzi = "" + hanzi_array[++hanzi_index];
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
                toggle_show();
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

        } catch (IndexOutOfBoundsException e) {
            hanzi_index = -1;
            word = Utility.getHardWord();
            hanzi_array = word.getHanzi().toCharArray();
            hanzi = "" + hanzi_array[++hanzi_index];
        }
        init();
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
    }
}

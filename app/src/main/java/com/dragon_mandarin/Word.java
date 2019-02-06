package com.dragon_mandarin;

import java.io.Serializable;

public class Word implements Serializable {
    private String hanzi;
    private String pinyin;
    private String translations;
    private String strokes;
    private String measure_word;

    public Word(String hanzi_, String pinyin_, String translations_, String strokes_, String measure_word_) {
        hanzi = hanzi_;
        pinyin = pinyin_;
        translations = translations_;
        strokes = strokes_;
        measure_word = measure_word_;
    }

    public String getHanzi() {
        return hanzi;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getTranslations() {
        return translations;
    }

    public String getStrokes() {
        return strokes;
    }

    public String getMeasureWord() {
        return measure_word;
    }
}
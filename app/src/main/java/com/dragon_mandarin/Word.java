package com.dragon_mandarin;

public class Word {
    private String hanzi;
    private String pinyin;
    private String description;
    private String strokes;
    private String measure_word;

    public Word(String hanzi_, String pinyin_, String description_, String strokes_, String measure_word_) {
        hanzi = hanzi_;
        pinyin = pinyin_;
        description = description_;
        strokes = strokes_;
        measure_word = measure_word_;
    }

    public String getHanzi() {
        return hanzi;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getDescription() {
        return description;
    }

    public String getStrokes() {
        return strokes;
    }

    public String getMeasure_word() {
        return measure_word;
    }
}
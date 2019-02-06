package com.dragon_mandarin;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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

    public static String favoritesFileName = "favorites.ser";

    // Serializes an object and saves it to a file
    public void saveToFavoritesFile(Context context) {
        ArrayList<Word> saved = readFromFavoritesFile(context);
        if (saved != null) saved.add(this);
        else {
            saved = new ArrayList<Word>();
            saved.add(this);
        }
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(favoritesFileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saved);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFromFavoritesFile(Context context) {
        ArrayList<Word> saved = readFromFavoritesFile(context);
        if (saved != null) {
            for (int i = 0; i < saved.size(); i++) {
                if (saved.get(i).getHanzi().equals(this.getHanzi())) saved.remove(i);
            }
            try {
                FileOutputStream fileOutputStream = context.openFileOutput(favoritesFileName, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(saved);
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Creates an object by reading it from a file
    public static ArrayList<Word> readFromFavoritesFile(Context context) {
        ArrayList<Word> words = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(favoritesFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            words = (ArrayList<Word>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }
}
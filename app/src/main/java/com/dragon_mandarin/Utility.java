package com.dragon_mandarin;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Utility {

    public static boolean simplified = true;

    private static ArrayList<Word> hardWords = new ArrayList<Word>();

    public static ArrayList<Word> getCEList(Context context) {
        ArrayList<Word> list = new ArrayList<Word>();
        JSONArray jsonArray = getCEJsonArray(context);
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject word = jsonArray.getJSONObject(i);
                String hanzi = word.getString("Simplified");
                String pinyin = word.getString("Pinyin");
                String translations = word.getString("Definition");
                list.add(new Word(hanzi, pinyin, translations, "", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Word> getHskList(int number, Context context) {
        ArrayList<Word> list = new ArrayList<Word>();
        JSONArray jsonArray = getHskJsonArray(number, context);
        list = jsonToList(jsonArray);
        return list;
    }

    public static ArrayList<Word> getAllHskList(Context context) {
        ArrayList<Word> list = new ArrayList<Word>();
        JSONArray jsonArray = getAllHskJsonArray(context);
        list = jsonToList(jsonArray);
        return list;
    }

    private static ArrayList<Word> jsonToList(JSONArray jsonArray) {
        ArrayList<Word> list = new ArrayList<Word>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject word = jsonArray.getJSONObject(i);
                String hanzi;
                if (simplified) hanzi = word.getString("hanzi");
                else hanzi = word.getString("hanzi_traditional");
                String pinyin = word.getString("pinyin");
                JSONArray t = word.getJSONArray("translations");
                String measureWord = "";
                String translations = "";
                for (int j = 0; j < t.length(); j++) {
                    String info = t.getString(j);
                    if (info.contains("[") || info.contains("CL:")) {
                        measureWord += " " + info;
                    }
                    else {
                        translations += " " + info;
                    }
                }
                list.add(new Word(hanzi, pinyin, translations, "", measureWord));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static JSONArray getHskJsonArray(int number, Context context) {
        try {
            JSONArray jsonArray = new JSONArray(readJSONFromAsset("json/hsk-level-" + Integer.toString(number) + ".json", context));
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO maybe not
        }
    }

    public static JSONArray getAllHskJsonArray(Context context) {
        try {
            JSONArray jsonArray = new JSONArray(readJSONFromAsset("hsk.json", context));
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO maybe not
        }
    }

    public static JSONArray getCEJsonArray(Context context) {
        try {
            JSONArray jsonArray = new JSONArray(readJSONFromAsset("cedict.json", context));
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // TODO maybe not
        }
    }

    private static String readJSONFromAsset(String file, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void newHardWord(Word word) {
        hardWords.add(word);
    }

    public static void removeHardWord(Word word) {
        ArrayList<Word> to_remove = new ArrayList<Word>();
        for (Word w : hardWords) {
            if (w.getHanzi().equals(word.getHanzi())) {
                to_remove.add(w);
            }
        }
        for (Word w : to_remove) {
            hardWords.remove(w);
        }
    }

    public static Word getHardWord() { //
        try {
            return hardWords.get((int) (Math.random() * (hardWords.size() - 1)));
        } catch (IndexOutOfBoundsException e) {
            return new Word("我", "wǒ", "I; me; my", "", ""); // TODO replace
        }
    }

    public static ArrayList<Word> gethardWords() {
        return new ArrayList<Word>(hardWords);
    }
}

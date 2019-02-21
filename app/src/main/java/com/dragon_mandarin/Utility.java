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

    public static String getNumberedPinyin(String original) { // TODO maybe add uppercase support?
        // REMOVES DOTS OVER U
        String numbered = "";
        String[] words = original.split(" ");
        for (String w : words) {
            String word = w.toLowerCase();
            if (word.contains("ā")) {
                numbered += word.replace("ā", "a") + 1;
            }
            if (word.contains("á")) {
                numbered += word.replace("á", "a") + 2;
            }
            if (word.contains("ǎ")) {
                numbered += word.replace("ǎ", "a") + 3;
            }
            if (word.contains("à")) {
                numbered += word.replace("à", "a") + 4;
            }
            if (word.contains("ē")) {
                numbered += word.replace("ē", "e") + 1;
            }
            if (word.contains("é")) {
                numbered += word.replace("é", "e") + 2;
            }
            if (word.contains("ě")) {
                numbered += word.replace("ě", "e") + 3;
            }
            if (word.contains("è")) {
                numbered += word.replace("è", "e") + 4;
            }
            if (word.contains("ī")) {
                numbered += word.replace("ī", "i") + 1;
            }
            if (word.contains("í")) {
                numbered += word.replace("í", "i") + 2;
            }
            if (word.contains("ǐ")) {
                numbered += word.replace("ǐ", "i") + 3;
            }
            if (word.contains("ì")) {
                numbered += word.replace("ì", "i") + 4;
            }
            if (word.contains("ō")) {
                numbered += word.replace("ō", "o") + 1;
            }
            if (word.contains("ó")) {
                numbered += word.replace("ó", "o") + 2;
            }
            if (word.contains("ǒ")) {
                numbered += word.replace("ǒ", "o") + 3;
            }
            if (word.contains("ò")) {
                numbered += word.replace("ò", "o") + 4;
            }
            if (word.contains("ū")) {
                numbered += word.replace("ū", "u") + 1;
            }
            if (word.contains("ú")) {
                numbered += word.replace("ú", "u") + 2;
            }
            if (word.contains("ǔ")) {
                numbered += word.replace("ǔ", "u") + 3;
            }
            if (word.contains("ù")) {
                numbered += word.replace("ù", "u") + 4;
            }
            if (word.contains("ǖ")) { // REMOVES DOTS FOR SIMPLICITY
                numbered += word.replace("ǖ", "u") + 1;
            }
            if (word.contains("ǘ")) {
                numbered += word.replace("ǘ", "u") + 2;
            }
            if (word.contains("ǚ")) {
                numbered += word.replace("ǚ", "u") + 3;
            }
            if (word.contains("ǜ")) {
                numbered += word.replace("ǜ", "u") + 4;
            }
            numbered += " ";
        }
        return numbered.trim();
    }

    public static String getSimplePinyin(String original) {
        return getNumberedPinyin(original).replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "");
    }
}

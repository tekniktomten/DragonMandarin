package com.dragon_mandarin;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Utility {

    public static ArrayList<Word> getHskList(int number, Context context) { // TODO should return words
        ArrayList<Word> list = new ArrayList<Word>();
        JSONArray jsonArray = getHskJsonArray(number, context);
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject word = jsonArray.getJSONObject(i);
                String hanzi = word.getString("hanzi");
                String pinyin = word.getString("pinyin");
                String translations = word.getJSONArray("translations").toString();
                list.add(new Word(hanzi, pinyin, translations, "", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Word> getAllHskList(Context context) { // TODO should return words
        ArrayList<Word> list = new ArrayList<Word>();
        JSONArray jsonArray = getAllHskJsonArray(context);
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject word = jsonArray.getJSONObject(i);
                String hanzi = word.getString("hanzi");
                String pinyin = word.getString("pinyin");
                String translations = word.getJSONArray("translations").toString();
                list.add(new Word(hanzi, pinyin, translations, "", ""));
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
}

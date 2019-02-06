package com.dragon_mandarin;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordListAdapter extends ArrayAdapter<Object> implements Filterable {
    //to reference the Activity
    private final Activity context;

    private final ArrayList<Word> words;

    private ArrayList<Word> displayedWords;

    public WordListAdapter(Activity context_, ArrayList<Word> words_) {
        super(context_, R.layout.listview_row, words_.toArray()); // TODO hmm?
        context = context_;
        words = words_;
        displayedWords = new ArrayList<Word>(words);
    }

    @Override
    public int getCount() {
        return displayedWords.size();
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView topTextField = (TextView) rowView.findViewById(R.id.wordListTextTop);
        TextView bottomTextField = (TextView) rowView.findViewById(R.id.wordListTextBottom);
        TextView imageView = (TextView) rowView.findViewById(R.id.wordListImage);
        LinearLayout row = (LinearLayout) rowView.findViewById(R.id.row);

        //this code sets the values of the objects to values from the arrays
        topTextField.setText(displayedWords.get(position).getPinyin());
        bottomTextField.setText(displayedWords.get(position).getTranslations());
        imageView.setText(displayedWords.get(position).getHanzi());
        row.setOnClickListener(new rowOnClickListener(displayedWords.get(position)));

        return rowView;

    };

    class rowOnClickListener implements View.OnClickListener {

        private Word word;

        public rowOnClickListener(Word word_) {
            word = word_;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, WordActivity.class);
            intent.putExtra("word", word);
            context.startActivity(intent);
        }
    }

    @Override // MAYBE NOT Override?
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Word> FilteredArrList = new ArrayList<Word>();

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = words.size();
                    results.values = words;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < words.size(); i++) {
                        String d = words.get(i).getTranslations();
                        String p = words.get(i).getPinyin();
                        String h = words.get(i).getHanzi();
                        if (d.toLowerCase().contains(constraint.toString()) || p.toLowerCase().contains(constraint.toString()) || h.contains(constraint.toString())) {
                            FilteredArrList.add(words.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                displayedWords = (ArrayList<Word>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }
        };
    }

    public int getNumberOfWords() {
        return words.size();
    }
}

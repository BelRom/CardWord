package com.example.cardword;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardword.model.Word;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by roman on 04.11.17.
 */

public class RecyclerDictionaryAdapter  extends RecyclerView.Adapter<RecyclerDictionaryAdapter.ViewHolder> {

    private static RealmResults<Word> mListWord;
    private static Context mContext;


    public RecyclerDictionaryAdapter(RealmResults<Word>  listWord, Context context) {
        mListWord = listWord;
        mContext = context;
    }

    @Override
    public RecyclerDictionaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mFirstItemTextView.setText(mListWord.get(position).getFirstWord());
        holder.mSecondItemTextView.setText(mListWord.get(position).getSecondWord());
    }

    @Override
    public int getItemCount() {
        return mListWord.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFirstItemTextView, mSecondItemTextView;

        public ViewHolder(View v) {
            super(v);
            mFirstItemTextView = (TextView) v.findViewById(R.id.firstItemTextView);
            mSecondItemTextView = (TextView) v.findViewById(R.id.secondItemTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(mContext, mListWord.get(getAdapterPosition()).getFirstWord(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}

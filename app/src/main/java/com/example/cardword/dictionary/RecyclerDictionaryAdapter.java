package com.example.cardword.dictionary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardword.R;
import com.example.cardword.model.Word;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by roman on 04.11.17.
 */

public class RecyclerDictionaryAdapter extends RecyclerView.Adapter<RecyclerDictionaryAdapter.ViewHolder> {

    private static RealmResults<Word> mListWord;
    private static Context mContext;
    private Realm mRealm;

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRealm.close();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public RecyclerDictionaryAdapter(RealmResults<Word> listWord, Context context) {
        mListWord = listWord;
        mContext = context;
    }

    @Override
    public RecyclerDictionaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        ViewHolder vh = new ViewHolder(v);

        mRealm = Realm.getDefaultInstance();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mFirstItemTextView.setText(mListWord.get(position).getFirstWord());
        holder.mSecondItemTextView.setText(mListWord.get(position).getSecondWord());
        holder.mCountItemTextView.setText(String.valueOf(mListWord.get(position).getCorrectAnswer()));
    }

    @Override
    public int getItemCount() {
        return mListWord.size();
    }

    public void removeItem(int position) {
        mRealm.beginTransaction();
        mListWord.deleteFromRealm(position);
        mRealm.commitTransaction();

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFirstItemTextView, mSecondItemTextView, mCountItemTextView;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;

        public ViewHolder(View v) {
            super(v);
            mFirstItemTextView = (TextView) v.findViewById(R.id.firstItemTextView);
            mSecondItemTextView = (TextView) v.findViewById(R.id.secondItemTextView);
            mCountItemTextView = (TextView) v.findViewById(R.id.countItemTextView);
            viewForeground = (LinearLayout) v.findViewById(R.id.view_foreground);
            viewBackground = (RelativeLayout) v.findViewById(R.id.view_background);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mListWord.get(getAdapterPosition()).getFirstWord(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}

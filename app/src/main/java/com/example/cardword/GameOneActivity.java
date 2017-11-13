package com.example.cardword;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardword.model.Word;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by roman on 08.11.17.
 */

public class GameOneActivity extends AppCompatActivity implements View.OnClickListener {

    private Realm realm;
    private Boolean mIsCorrect = false;
    private RealmResults<Word> mList;
    private int mCount = 0;
    private Word word;
    private Random random;


    @BindView(R.id.buttonCorrect) Button mBtnCorrect;
    @BindView(R.id.buttonNext) Button mBtnNext;
    @BindView(R.id.buttonWrong) Button mBtnWrong;
    @BindView(R.id.TranslateTextView) TextView mTranslateTextView;
    @BindView(R.id.WordtextView) TextView mWordTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_one);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        mList = realm.where(Word.class)
                .lessThan("correctAnswer", 5)
                .findAll();

         random = new Random();


        setText();
        mBtnCorrect.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnWrong.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCorrect:
                mTranslateTextView.setBackgroundColor(getResources().getColor(R.color.bg_frame_green));
                mWordTextView.setBackgroundColor(getResources().getColor(R.color.bg_frame_green));
                mIsCorrect = true;
                break;

            case R.id.buttonWrong:
                mTranslateTextView.setBackgroundColor(getResources().getColor(R.color.bg_frame_red));
                mWordTextView.setBackgroundColor(getResources().getColor(R.color.bg_frame_red));
                mIsCorrect = false;
                break;

            case R.id.buttonNext:
                setText();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }



    void setText(){
        if(mList.size() < 1){
            Toast.makeText(this, "Добавтье слов в словарь", Toast.LENGTH_LONG).show();

        } else if (mCount < 10 && mCount < mList.size()){
            if (mIsCorrect){

                realm.beginTransaction();
                word.setCorrectAnswer(word.getCorrectAnswer()+1);
                realm.commitTransaction();
            }
            mTranslateTextView.setBackgroundColor(getResources().getColor(R.color.white));
            mWordTextView.setBackgroundColor(getResources().getColor(R.color.white));
            word = mList.get(random.nextInt(mList.size()));
            mWordTextView.setText(word.getFirstWord());
            mTranslateTextView.setText(word.getSecondWord());
            mCount++;

        } else {
            Toast.makeText(this, "Конец игры", Toast.LENGTH_LONG).show();
            finish();
        }

    }



}

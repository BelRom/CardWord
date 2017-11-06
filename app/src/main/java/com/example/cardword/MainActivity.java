package com.example.cardword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cardword.dictionary.DictionaryActivity;
import com.example.cardword.model.Word;
import com.example.cardword.model.Translate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {


    @BindView(R.id.textInput) EditText mTextInpute;
    @BindView(R.id.textAnswer) EditText mTextTranslated;
    @BindView(R.id.btnTranslate) Button mTranslate;
    @BindView(R.id.btnDelete) Button mBtnDelete;
    @BindView(R.id.btnDictionary) Button mBtnDictionary;
    @BindView(R.id.btnRefresh) Button mBtnRefresh;
    @BindView(R.id.btnSave) Button mBtnSave;
    @BindView(R.id.btnUpdate) Button mBtnUpdate;
    @BindView(R.id.spinFerstLanguge) Spinner mFerstLanguageSpinner;
    @BindView(R.id.spinSecondLanguge) Spinner mSecondLanguageSpinner;
    private String mFerstLangugeCode, mSecondLangugeCode;
    private List<String> Convert1List = new ArrayList<>();
    static Map<String, String> mLanguageMap = new LinkedHashMap<>();
    private Realm realm;
    private final String URL = "https://translate.yandex.net";
    private final String KEY =
            "trnsl.1.1.20170909T092720Z.30d670e6e7138cf8.db2be5189bbc64c2c1305c21d160b01aed2b89eb";
    private Link mIntr;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mIntr = retrofit.create(Link.class);

        setLanguageCod();
        spinnerAddCod();
        mBtnDelete.setOnClickListener(this);
        mBtnDictionary.setOnClickListener(this);
        mBtnRefresh.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mTranslate.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnTranslate:
                translateCall();
                break;

            case  R.id.btnDictionary:
                Intent intent = new Intent(this, DictionaryActivity.class);
                startActivity(intent);
                break;

            case  R.id.btnRefresh:
                break;

            case  R.id.btnSave:

                Word word = new Word();
                word.setFirstWord(mTextInpute.getText().toString());
                word.setSecondWord(mTextTranslated.getText().toString());
                word.setCorrectAnswer(0);
                realm.beginTransaction();
                realm.insert(word);
                realm.commitTransaction();

                break;

            case  R.id.btnUpdate:
                break;

        }
    }

    void translateCall(){
        Call<Translate> call = mIntr.translate(KEY, mTextInpute.getText().toString(),
                mFerstLangugeCode +"-"+ mSecondLangugeCode);
        call.enqueue(new Callback<Translate>() {
            @Override
            public void onResponse(Call<Translate> call, Response<Translate> response) {

                if (response.isSuccessful()) {
                    Translate translate = response.body();
                    String string = translate.getText().toString();
                    string = string.substring(1, string.length()-1);
                    mTextTranslated.setText(string);
                }
            }

            @Override
            public void onFailure(Call<Translate> call, Throwable t) {

            }
        });

    }

     void setLanguageCod(){
        mLanguageMap.put("Русский","ru");
        mLanguageMap.put("English","en");
        mLanguageMap.put("Azerbaijani","az");
        mLanguageMap.put("Albanian","sq");

    }


    void spinnerAddCod(){
        Convert1List.addAll(mLanguageMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Convert1List);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFerstLanguageSpinner.setAdapter(adapter);
        mSecondLanguageSpinner.setAdapter(adapter);

        mFerstLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mFerstLangugeCode = mLanguageMap.get(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSecondLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSecondLangugeCode = mLanguageMap.get(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}

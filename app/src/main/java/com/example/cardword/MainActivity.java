package com.example.cardword;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {
//implements View.OnClickListener

    EditText mTextInpute;
    @BindView(R.id.textAnswer) EditText mTextTranslate;
    Button mTranslate;
    @BindView(R.id.btnDelete) Button mBtnDelete;
    @BindView(R.id.btnDictionary) Button mBtnDictionary;
    @BindView(R.id.btnRefresh) Button mBtnRefresh;
    @BindView(R.id.btnSave) Button mBtnSave;
    @BindView(R.id.btnUpdate) Button mBtnUpdate;
    private final String URL = "https://translate.yandex.ru";
    private final String KEY =
            "trnsl.1.1.20170909T092720Z.30d670e6e7138cf8.db2be5189bbc64c2c1305c21d160b01aed2b89eb";


    Gson gson = new GsonBuilder().create();

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
    logging.
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // add your other interceptors …

    // add logging as last interceptor
    httpClient.addInterceptor(logging);
    // <-- this is the important line!
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private Link intr = retrofit.create(Link.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTranslate = (Button) findViewById(R.id.BtnTranslate);
        mTextInpute = (EditText) findViewById(R.id.textInput);


//        mBtnDelete.setOnClickListener(this);
//        mBtnDictionary.setOnClickListener(this);
//        mBtnRefresh.setOnClickListener(this);
//        mBtnSave.setOnClickListener(this);
//        mBtnUpdate.setOnClickListener(this);

//        List<Word> listword = new ArrayList<>();
//        listword.add(new Word("word", "слово"));
        mTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback();


            }
        });



    }



//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//
//            case R.id.BtnTranslate:
//                break;
//
//            case  R.id.btnDictionary:
//                Intent intent = new Intent(this, Dictionary.class);
//                break;
//
//            case  R.id.btnRefresh:
//                break;
//
//            case  R.id.btnSave:
//
//                break;
//
//            case  R.id.btnUpdate:
//                break;
//
//        }
//    }

    void callback(){
        Call<Words> call = intr.translate(KEY, mTextInpute.getText().toString(), "ru-en");
        call.enqueue(new Callback<Words>() {
           @Override
           public void onResponse(Call<Words> call, Response<Words> response) {
               if(response.isSuccessful()) {
                   mTextInpute.setText("success");
                   Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);

                   for (Map.Entry e : map.entrySet()) {
                       if (e.getKey() == "test") {
                           mTextInpute.setText(e.getValue().toString());
                       }
                   }
               }
           }

           @Override
           public void onFailure(Call<Words> call, Throwable t) {
               mTextInpute.setText("onFailure");
           }
       });

    }
}

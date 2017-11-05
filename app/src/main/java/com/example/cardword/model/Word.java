package com.example.cardword.model;

import io.realm.RealmObject;

/**
 * Created by roman on 01.11.17.
 */

public class Word extends RealmObject {
    private String firstWord;
    private String secondWord;
    private int correctAnswer;

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public void setSecondWord(String secondWord) {
        this.secondWord = secondWord;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Word{" +
                "firstWord='" + firstWord + '\'' +
                ", secondWord='" + secondWord + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}

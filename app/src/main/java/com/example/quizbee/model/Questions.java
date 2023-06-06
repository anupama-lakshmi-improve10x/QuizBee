package com.example.quizbee.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Questions {

    private Integer number;

    private String question;

    private ArrayList<String> answers;

    @SerializedName("correct_answer")
    private Integer correct_answer;
}

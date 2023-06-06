package com.example.quizbee.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Quiz {

   @SerializedName("_id")
    private String serviceId;

    private Module module;

    private ArrayList<Questions> questions;
}

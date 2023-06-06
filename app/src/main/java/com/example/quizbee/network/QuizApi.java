package com.example.quizbee.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizApi {

    public QuizApiService createApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://crudcrud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QuizApiService apiService = retrofit.create(QuizApiService.class);
        return apiService;
    }
}

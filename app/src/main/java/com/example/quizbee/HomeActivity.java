package com.example.quizbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizbee.databinding.ActivityHomeBinding;
import com.example.quizbee.question.QuestionsActivity;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("QuizBee");
        handleStartBtn();
    }

    public void handleStartBtn() {
        binding.startBtn.setOnClickListener(v -> {
            if(binding.nameTxt.getText().toString().trim().equals("") == false) {
                Intent intent = new Intent(this, QuestionsActivity.class);
                startActivity(intent);
            }
        });
    }
}
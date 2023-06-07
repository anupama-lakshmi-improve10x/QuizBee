package com.example.quizbee.question;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbee.databinding.QuestionItemBinding;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    QuestionItemBinding binding;

    public QuestionViewHolder(@NonNull QuestionItemBinding questionItemBinding) {
        super(questionItemBinding.getRoot());
        binding = questionItemBinding;
    }
}

package com.example.quizbee.question;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbee.databinding.QuestionItemBinding;
import com.example.quizbee.model.Question;
import com.example.quizbee.model.Quiz;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private List<Question> questions;

    private OnItemActionListener onItemActionListener;

    void setData(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    void OnItemActionListener(OnItemActionListener onItemActionListener){
        this.onItemActionListener = onItemActionListener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionItemBinding binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(binding);
        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question questionList = questions.get(position);
        holder.binding.numberTxt.setText(String.valueOf(position +1));
        holder.binding.getRoot().setOnClickListener(v -> {
            onItemActionListener.onClicked(questionList);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}

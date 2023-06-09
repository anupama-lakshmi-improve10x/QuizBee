package com.example.quizbee.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quizbee.R;
import com.example.quizbee.databinding.ActivityQuestionsBinding;
import com.example.quizbee.model.Question;
import com.example.quizbee.model.Quiz;
import com.example.quizbee.network.QuizApi;
import com.example.quizbee.network.QuizApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {

    private List<Question> questions = new ArrayList<>();
    private ActivityQuestionsBinding binding;

    private QuestionAdapter questionAdapter;

    private int currentQuestionPosition = 0;

    private Integer[] answerOptionsIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Question");
        fetchQuestions();
        setUpQuestionAdaptor();
        setUpQuestionsRv();
        setNextBtn();
        setPreviousBtn();
        handleRadioGroup();
    }

    private void setUpQuestionAdaptor() {
        questionAdapter = new QuestionAdapter();
        questionAdapter.setData(new ArrayList<>());
        questionAdapter.OnItemActionListener(new OnItemActionListener() {
            @Override
            public void onClicked(Question question) {
                showData(question);
            }
        });
    }

    private void setUpQuestionsRv() {
        binding.numberRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.numberRv.setAdapter(questionAdapter);
    }

    private void fetchQuestions() {
        QuizApi quizApi = new QuizApi();
        QuizApiService quizApiService = quizApi.createApiService();
        Call<List<Quiz>> call = quizApiService.fetchQuizData();
        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                if (response.isSuccessful()) {
                    questions = response.body().get(0).getQuestions();
                    questionAdapter.setData(questions);
                    answerOptionsIndexes = new Integer[questions.size()];
                    showData(questions.get(0));
                    Toast.makeText(QuestionsActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(QuestionsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showData(Question question) {
        // Load the question and answer
        setQuestions(question);
        //Highlight the question Number and previous question
        setColor(question);
        //Handle the previous button visibility
        setPreviousBtnVisibility();
        //Handle the next button visibility
       setNextBtnVisibility();
    }
    private void setNextBtn() {
            binding.nextBtn.setOnClickListener(v -> {
                try {
                    currentQuestionPosition++;
                    Question question = questions.get(currentQuestionPosition);
                    showData(question);
                } catch (Exception e) {
                    Toast.makeText(this, "Questions Completed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setPreviousBtn() {
        binding.previousBtn.setOnClickListener(v -> {
            try{
                currentQuestionPosition--;
                Question question = questions.get(currentQuestionPosition);
                showData(question);
            } catch (Exception e) {
                Toast.makeText(this, "No questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setQuestions(Question question) {
        binding.questionTxt.setText(question.getQuestion());
        binding.radioGroupRg.clearCheck();
        binding.ansRbtn1.setText(question.getAnswers().get(0));
        binding.ansRbtn2.setText(question.getAnswers().get(1));
        binding.ansRbtn3.setText(question.getAnswers().get(2));
        binding.ansRb4.setText(question.getAnswers().get(3));
        if(answerOptionsIndexes[currentQuestionPosition] != null) {
            if(answerOptionsIndexes[currentQuestionPosition] == 0) {
                binding.ansRbtn1.setChecked(true);
            } else if (answerOptionsIndexes[currentQuestionPosition] == 1) {
                binding.ansRbtn2.setChecked(true);
            } else if(answerOptionsIndexes[currentQuestionPosition] == 2) {
                binding.ansRbtn3.setChecked(true);
            } else if(answerOptionsIndexes[currentQuestionPosition] == 3) {
                binding.ansRb4.setChecked(true);
            }
        }
    }

    private void setColor(Question question) {
        currentQuestionPosition = question.getNumber()-1;
        questionAdapter.currentQuestionPosition = this.currentQuestionPosition;
        questionAdapter.notifyDataSetChanged();
    }

    private void setPreviousBtnVisibility() {
        if(currentQuestionPosition == 0){
            binding.previousBtn.setVisibility(View.INVISIBLE);
        } else {
            binding.previousBtn.setVisibility(View.VISIBLE);
        }
    }

    private void setNextBtnVisibility() {
        if(currentQuestionPosition == questions.size()-1) {
            binding.nextBtn.setVisibility(View.INVISIBLE);
        } else {
            binding.nextBtn.setVisibility(View.VISIBLE);
        }
    }

    private void handleRadioGroup() {
        binding.radioGroupRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(binding.ansRbtn1.isChecked()) {
                   answerOptionsIndexes[currentQuestionPosition] = 0;
               } else if(binding.ansRbtn2.isChecked()) {
                   answerOptionsIndexes[currentQuestionPosition] = 1;
               } else if(binding.ansRbtn3.isChecked()) {
                   answerOptionsIndexes[currentQuestionPosition] = 2;
               } else if(binding.ansRb4.isChecked()) {
                   answerOptionsIndexes[currentQuestionPosition] = 3;
               }
            }
        });
    }
}
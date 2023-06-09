package com.example.quizbee.question;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    private int currentQuestion = 0;

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
        binding.questionTxt.setText(question.getQuestion());
        binding.ansRbtn1.setText(question.getAnswers().get(0));
        binding.ansRbtn2.setText(question.getAnswers().get(1));
        binding.ansRbtn3.setText(question.getAnswers().get(2));
        binding.ansRb4.setText(question.getAnswers().get(3));
    }
    private void setNextBtn() {
            binding.nextBtn.setOnClickListener(v -> {
                try {
                    currentQuestion = questionAdapter.currentQuestionPosition;
                    currentQuestion++;
                    Question question = questions.get(currentQuestion-1);
                    showData(question);
                    questionAdapter.currentQuestionPosition = currentQuestion;
                    questionAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(this, "Questions Completed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setPreviousBtn() {
        binding.previousBtn.setOnClickListener(v -> {
            try{
                currentQuestion = questionAdapter.currentQuestionPosition;
                currentQuestion--;
                Question question = questions.get(currentQuestion);
                showData(question);
                questionAdapter.currentQuestionPosition = currentQuestion;
                questionAdapter.notifyDataSetChanged();
                if(currentQuestion == 0){
                    binding.previousBtn.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Toast.makeText(this, "No questions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

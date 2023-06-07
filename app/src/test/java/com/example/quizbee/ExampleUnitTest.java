package com.example.quizbee;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quizbee.model.Quiz;
import com.example.quizbee.network.QuizApi;
import com.example.quizbee.network.QuizApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test

    public void getQuiz() throws IOException {
        QuizApi quizApi = new QuizApi();
        QuizApiService quizApiService = quizApi.createApiService();
        Call<List<Quiz>> call = quizApiService.fetchQuizData();
        List<Quiz> quizList = call.execute().body();
        assertNotNull(quizList);
        assertFalse(quizList.isEmpty());
        System.out.println(new Gson().toJson(quizList));
    }
}
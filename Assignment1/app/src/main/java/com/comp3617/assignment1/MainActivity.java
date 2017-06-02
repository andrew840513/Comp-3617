package com.comp3617.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//answer 23132
    private static String LOGTAG = "Assignment";
    private static int QUESTION_COUNT;
    private static int ANSWERS_COUNT = 3;

    private int currentQuestion;
    private int answers[] = {2,3,1,3,2};
    private int usersAnswers[];

    private String questions[];
    private String answerTextGroup[][];

    private TextView question;

    private RadioButton answer1, answer2, answer3;
    private RadioButton answerGroup[];
    private RadioGroup radioGroup;

    public void startAction(View view){
        currentQuestion = 0;
        question.setText(questions[0]);
        for(int i = 0; i<ANSWERS_COUNT; i++){
            answerGroup[i].setVisibility(View.VISIBLE);
            answerGroup[i].setText(getResources().getStringArray(R.array.Question1)[i]);
        }
        radioGroup.clearCheck();
        findViewById(R.id.nextBtn).setVisibility(View.VISIBLE);
    }

    public void nextAction(View view){
        if(usersAnswers[currentQuestion] == 0){
            Toast.makeText(this, "Please choose a answer", Toast.LENGTH_SHORT).show();
        }else if(currentQuestion <QUESTION_COUNT -1) {
            prepareNextQuestion();
        }else{
            int score = calculateScore(answers,usersAnswers);
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
         }
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.answer1:
                if (checked)
                    usersAnswers[currentQuestion] = 1;
                break;
            case R.id.answer2:
                if (checked)
                    usersAnswers[currentQuestion] = 2;
                break;
            case R.id.answer3:
                if (checked)
                    usersAnswers[currentQuestion] = 3;
                break;
        }

    }

    public void prepareNextQuestion(){
        currentQuestion++;
        question.setText(questions[currentQuestion]);
        for(int i = 0; i<ANSWERS_COUNT; i++){
            answerGroup[i].setText(answerTextGroup[currentQuestion][i]);
        }
        radioGroup.clearCheck();
    }

    public int calculateScore(int[] answers, int[] usersAnswer){
        int score = 0;
        for(int i =0; i<QUESTION_COUNT; i++){
            if(answers[i] == usersAnswer[i]){
                score++;
            }
        }
        return score;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questions = getResources().getStringArray(R.array.questions);
        question = (TextView) findViewById(R.id.displayQuestion);
        answer1 = (RadioButton) findViewById(R.id.answer1);
        answer2 = (RadioButton) findViewById(R.id.answer2);
        answer3 = (RadioButton) findViewById(R.id.answer3);
        answerGroup = new RadioButton[] {answer1,answer2,answer3};
        answerTextGroup = new String[][]{getResources().getStringArray(R.array.Question1),getResources().getStringArray(R.array.Question2),getResources().getStringArray(R.array.Question3),getResources().getStringArray(R.array.Question4),getResources().getStringArray(R.array.Question5)};
        QUESTION_COUNT = questions.length;
        usersAnswers = new int[QUESTION_COUNT];
        radioGroup = (RadioGroup)findViewById(R.id.radioBtnGroup);
    }
}

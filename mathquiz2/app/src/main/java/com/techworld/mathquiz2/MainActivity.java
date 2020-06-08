package com.techworld.mathquiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_start, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_timer, tv_question, tv_score, tv_bottommessage;
    ProgressBar prog_timer;

    Game g = new Game();

    int secondRemaining = 30;


    CountDownTimer timer = new CountDownTimer(31000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondRemaining--;
            tv_timer.setText(Integer.toString(secondRemaining) + "sec");
            prog_timer.setProgress( 30 - secondRemaining);

        }

        @Override
        public void onFinish() {
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);
            tv_bottommessage.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions()-1));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);

                }
            },0);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);

        tv_timer = findViewById(R.id.tv_timer);
        tv_score = findViewById(R.id.tv_score);
        tv_question = findViewById(R.id.tv_question);
        tv_bottommessage = findViewById(R.id.tv_bottommessage);
        prog_timer = findViewById(R.id.prog_timer);

        tv_timer.setText("0sec");
        tv_question.setText("");
        tv_score.setText("0pts");
        tv_bottommessage.setText("Press GO!");


        View.OnClickListener startButtonClickListener  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;

                start_button.setVisibility(View.INVISIBLE);
                secondRemaining = 30;
                g = new Game();
                nextTurn();
                timer.start();
            }
        };

        View.OnClickListener answerButtonClickListener  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button buttonClicked = (Button) v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()) + "pts");
                nextTurn();



            }
        };

        btn_start.setOnClickListener(startButtonClickListener);

        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);

    }

    private void nextTurn() {
        g.makeNewQuestion();
        int [] answer = g.getCurrentQuestion().getAnswerArray();

        btn_answer0.setText(Integer.toString(answer[0]));
        btn_answer1.setText(Integer.toString(answer[1]));
        btn_answer2.setText(Integer.toString(answer[2]));
        btn_answer3.setText(Integer.toString(answer[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);


        tv_question.setText(g.getCurrentQuestion().getQuestionPhrase());
        tv_bottommessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() -1));



    }
}


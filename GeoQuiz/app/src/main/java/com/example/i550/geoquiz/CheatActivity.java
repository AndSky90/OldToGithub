package com.example.i550.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;
    private static final String EXTRA_ANSWER_IS_TRUE="com.example.i550.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN="com.example.i550.geoquiz.answer_shown";
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
            Intent intent = new Intent(packageContext,CheatActivity.class);
            intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
            return intent;
    }
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);        //сукабля чтоб родитель декодировал возврат интента
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        //присваиваем переменной данные из интента
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = findViewById(R.id.answerTV);
        mShowAnswerButton=findViewById(R.id.show_ans_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.atrue);
                } else{
                    mAnswerTextView.setText(R.string.afalse);
                }
                setAnswerShownResult(true); //возвращаем результат интента
            }
        });
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);              //идиотизм ну и ладно
    }
}

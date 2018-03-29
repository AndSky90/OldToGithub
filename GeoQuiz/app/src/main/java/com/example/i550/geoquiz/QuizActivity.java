package com.example.i550.geoquiz;

        import android.app.Activity;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String KEY_INDEX = "index";
    private static final int REQ_CODE_CHEAT = 0;    //типа идентификатора интента, вдруг их много
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton, mCheatButton;
    private TextView mQTV;
    private Quests[] mQuestionBank = new Quests[]{
            new Quests(R.string.q_australia,true),
            new Quests(R.string.q_oceans,true),
            new Quests(R.string.q_mideast,false),
            new Quests(R.string.q_africa,false),
            new Quests(R.string.q_americas,true),
            new Quests(R.string.q_asia,true)
    };
    private byte[] ans = new byte[]{-1,-1,-1,-1,-1,-1};
    private byte countAns = 0;
    private int mCurrentIndex = 0;
    private boolean mCheater = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQTV = findViewById(R.id.qtv);
        mNextButton=findViewById(R.id.nextbutton);
        mPrevButton=findViewById(R.id.prevbutton);
        mTrueButton=findViewById(R.id.truebutton);
        mFalseButton=findViewById(R.id.falsebutton);
        mCheatButton=findViewById(R.id.cheatbutton);
        if (savedInstanceState!=null){ mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);}
        updateQuestion();

        mQTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();     //присваиваем переменной ответ на вопрос,
                //эта активити не должна знать реализацию метода приемки интента, поэтому пишем ее в той активити
                //назначаем интенту метод той активити
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent,REQ_CODE_CHEAT);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                checkAnswer(false);
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() //в скобках - создается новый листнер и описывается его метод  онклк
        {
            @Override public void onClick(View view)
            {
                checkAnswer(true);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
               updateQuestion();
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex-1)%mQuestionBank.length;
                if (mCurrentIndex<0) {mCurrentIndex=0;}
                updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        if(ans[mCurrentIndex]!=-1){
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
        }else{
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
        }
        mQTV.setText(question);
    }
    private void checkAnswer(boolean userPressed)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if(mCheater){
            messageResId=R.string.cheater;
        }else
            {
        if(userPressed==answerIsTrue){
            messageResId=R.string.ctoast;
            ans[mCurrentIndex]=1;
        }else{
            messageResId=R.string.ictoast;
            ans[mCurrentIndex]=0;
        }   }
        Toast.makeText(QuizActivity.this,messageResId,Toast.LENGTH_SHORT).show();
        countAns++;

        if(countAns==mQuestionBank.length){
            int totalResult=0;
            for (byte i:ans){
                totalResult+=i;
            }
            String totalRes=Integer.toString(totalResult*100/mQuestionBank.length)+"%" ;
            Toast.makeText(QuizActivity.this,totalRes,Toast.LENGTH_SHORT).show();
        }
        mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
        updateQuestion();
    }
    @Override public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!=Activity.RESULT_OK){return;}
        if(requestCode==REQ_CODE_CHEAT){
            if(data==null){return;}
            mCheater=CheatActivity.wasAnswerShown(data);
        }
    }
}

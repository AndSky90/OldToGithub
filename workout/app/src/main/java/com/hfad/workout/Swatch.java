package com.hfad.workout;

import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//чтобы фрагмент реагировал на кнопки, имплементируем такой интерфейс
public class Swatch extends Fragment implements View.OnClickListener {  //из активити во фрагмент переделывали тут
    private int seconds=0;
    private boolean running;
    private boolean wasRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {   //вместо протектед
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);       -это ненадо
        if (savedInstanceState!=null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            if(wasRunning){running=true;}
        }
      //  runTimer();       - не вызываем тк макета ещё нет
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle SavedInstanceState)
    {
        View layout = inflater.inflate(R.layout.swatch,container,false);
        runTimer(layout);
//связываем листенер с кнопками
        Button startButton = layout.findViewById(R.id.startb);//находим вью
        startButton.setOnClickListener(this); //устанавливаем вьюшке листенер с параметром (объект листенер)
        //так как этот фрагмент имплементирует листенер, пишем this
        Button stopButton = layout.findViewById(R.id.stopb);
        stopButton.setOnClickListener(this);
        Button resetButton = layout.findViewById(R.id.resetb);
        resetButton.setOnClickListener(this);
        return layout;
    }
    @Override public void onPause()
    {
        super.onPause();
        wasRunning=running;
        running=false;
    }
    @Override public void onResume()
    {
        super.onResume();
        if (wasRunning) {running = true;}
    }
    @Override public void onClick(View v)   //тут параметр = представление в котором щёлкнули по ид
    {
        switch (v.getId()){     //выбираем из (ИД получаемого из вью щелкнутого)
            case R.id.startb:
                onClickStart(v);
                break;
            case R.id.stopb:
                onClickStop(v);
                break;
            case R.id.resetb:
                onClickReset(v);
                break;

        }
    }

    public void onClickStart(View view ) {
        running=true;
    }

    public void onClickStop(View view) {
        running=false;
    }

    public void onClickReset(View view) {
        running=false;
        seconds=0;
    }

    public void runTimer(View view) {               //добавили параметр, рантаймеру передается объект вью
        final TextView times = view.findViewById(R.id.timeView); //теперь вызываем у вью
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int h = seconds / 3600;
                int m = (seconds % 3600) / 60;
                int s = seconds % 60;
                String time = String.format("%d:%02d:%02d", h, m, s);
                times.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }
}
package com.hfad.workout;

import android.app.Activity;
import android.os.Bundle;

import com.hfad.workout.Workout;

public class DetActivity extends Activity {
public static final String EXTRA_WORKOUT_ID = "id"; //ебучая константа
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//вызываем родительский код инициализации
        setContentView(R.layout.activity_det);//назначаем  xml
        DetailFragment wdf = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        //получаем ссылку на фрагмент(приводим к классу фрагмента).метод диспетчера фрагментов
        int woId = (int) getIntent().getExtras().get(EXTRA_WORKOUT_ID); //может вернуть нулл
        //получаем идентификатор из интента, запустившего эту активность
        wdf.setWorkout(woId);//передаем wdf-у (фрагменту) этот идентификатор (мой)
    }
}

package com.hfad.workout;


import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.workout.Workout;

public class DetailFragment extends Fragment
{
    private long workoutId;
    public DetailFragment() {}        // Required empty public constructor

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                //чайлдменеджер фрагмента требует апи17

    @Override public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
    //действия при инициализации фрагмента
    {
        if(savedInstanceState!=null){workoutId=savedInstanceState.getLong("WorkoutId");}
        //если в бандле чтото есть, подтягиваем ранее сохраненную локал переменную
        //если в бандле ничего нет то не заменяем фрагмент, а делаем транзакцию:
        else    //то есть если без элса то при повороте экрана счетчик обнуляется
            {
            //создаем дочерню транзакцию для отображения фрагмента счетчика в фрагменте детали
            //эта транзакция вкладывается в основную, в итоге для системы будто она одна, в стеке возврата только основная
            FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();  //начинаем транзакцию из менеджера фрагмента-родителя
            Swatch swatchFrag = new Swatch();   //создаем экземпляр фрагмента
            ft1.replace(R.id.swt_container, swatchFrag);     //swt_container - название этого (дочернего) фрейма в xml родителя
            //заменяем фрагмент в фрейме
            ft1.addToBackStack(null);
            ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);   //стиль анимации
            ft1.commit();       //поехали!!!
            }
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override public void onStart()
    {
        super.onStart();
        View view = getView();
        if (view!=null)
        {
            Workout workout = Workout.wos[(int)workoutId];
            TextView title = view.findViewById(R.id.textTitle);
            title.setText(workout.getName());
            TextView descr = view.findViewById(R.id.textDescr);
            descr.setText(workout.getDescr());
        }

    }


    public void setWorkout(long id){
        this.workoutId=id;
    }

    @Override public void onSaveInstanceState(Bundle savedInstanceState)
    //вызывается перед уничтожением фрагмента, например при повороте экрана
    //переопределяем стандартный метод: засовываем в bundle переменную, которую надо сохранить
    {
        savedInstanceState.putLong("WorkoutId", workoutId);
    }
}

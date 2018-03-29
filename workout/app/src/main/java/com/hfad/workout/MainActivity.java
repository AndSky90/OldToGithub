package com.hfad.workout;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity implements ListFr.WLListener
{
    private ShareActionProvider sap;    //для шейра

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DetailFragment frag = (DetailFragment)getFragmentManager().findFragmentById(R.id.detail_frag);
        //frag.setWorkout(1);
    }

    public void iClicked(long id) {
        View fragCont = findViewById(R.id.frag_container);  //ссылка на фрейм содержащий DetailFragment (фрагмент)
        if (fragCont != null) {    //если фрейм существует, то макет тот, и делать:
            DetailFragment details = new DetailFragment();          //создание нового экземпляра фрагмента, кот надо отобразить
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //---(название явафайла фрагмента) диспетчер фрагментов родителя(активности).Открыть ft - (транзакция фрагмента)
            details.setWorkout(id);     //наш фрагмент.выбираем экземпляр данных
            ft.replace(R.id.frag_container, details);       //замена фрагмента
            ft.addToBackStack(null);                        //добавили в стек возврата
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);    //включение анимации
            ft.commit();    //закрепить транзакцию
        } else {
            Intent intent = new Intent(this, DetActivity.class); // создаем интент, (контекст, кому)
            intent.putExtra(DetActivity.EXTRA_WORKOUT_ID, (int) id);      //создаем экстру(ключ.значение)
            startActivity(intent);      //стартуем интент "intent"
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) //создаем чтоб работало меню в шапке
    {         //добавляем элемент на панель
        getMenuInflater().inflate(R.menu.menu_main,menu);   //(файл ресурсов меню =хмл, добавляет его в объект Menu)
        //inflater надувает вью из файла ресурса
     *//*   MenuItem mi = menu.findItem(R.id.a_share);  //находит в меню ресурс
        sap=(ShareActionProvider)mi.getActionProvider();    //находим провайдера и присваиваем "sap"
        setIntent2("examplya");*//*
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent2(String text){            //интент передает текст шейру
        Intent intentus = new Intent(Intent.ACTION_SEND);
        intentus.setType("text/plain");
        intentus.putExtra(intentus.EXTRA_TEXT, text);
        sap.setShareIntent(intentus);
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item)     //обработка клика на (пункте)
    {
        switch (item.getItemId())   // получаем ID у MenuItem по которому кликнули
        {
            case R.id.action_settings:     // действия в зависимости...
                return true;                // значит что щелчок обработан
            case R.id.co:
                    //запускаем интентом ордер-активити
                Intent intent = new Intent(this,OrderActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);*/
        }
   /* }*/
/*}*/


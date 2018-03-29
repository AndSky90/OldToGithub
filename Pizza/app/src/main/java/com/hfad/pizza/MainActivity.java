package com.hfad.pizza;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private String[] titles;
    private ListView drList;
    private DrawerLayout drawerLo = findViewById(R.id.drawer_layout);

    private class DICL implements ListView.OnItemClickListener      // имплементируем слушателя
    {
        @Override public void onItemClick(AdapterView<?> parent, View view, int pos, long id)     //реализуем его метод
        {selectItem(pos);}   //выполняется при нажатии на итем
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        drList = findViewById(R.id.drawer);
        drList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles));
        //заполняем дравер посредством АррайАдаптера
        // симпл_лист_итем - выделяется подсветкой если нажал
        drList.setOnItemClickListener(new DICL());
        if(savedInstanceState==null){selectItem(0);}    //если первый запуск то отображает мэйн
    }

    private void selectItem(int pos)
    {
        Fragment fragment;
        switch (pos)
        {
            case 1:
                fragment=new PizzaFragment();
                break;
            case 2:
                fragment=new PastaFragment();
                break;
            case 3:
                fragment=new StoresFragment();
                break;
            default:
                fragment=new TopFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_fr,fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionTitleBar(pos);     //поменять заголовок
        drawerLo.closeDrawer(drList);     // drList - выдвижная панель, закрываем ее
    }

    private void setActionTitleBar(int pos)         //заголовок панели
    {
    String title;
    if (pos==0) {title = getResources().getString(R.string.app_name);}  //по умолчанию
    else {title=titles[pos];}       //берем имя из итема массива
    getActionBar().setTitle(title);
    }

    ActionBarDrawerToggle drT = new ActionBarDrawerToggle(this, drawerLo, R.string.Open, R.string.Close)
    {
        //при переходе панели к закрытию
        @Override
        public void onDrawerClosed(View view)
        {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();        //создать заново меню команд нахуято
            //выполняется при закрытии панели
        }
        //при переходе панели к открытию
        @Override
        public void onDrawerOpened(View drawerView)
        {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
            //выполняется при открытии панели
        }
    };
    drawerLo.setDrawerListener(drT);        //назначить дт слушателем выдвижной панели

    @Override public boolean onPrepareOptionsMenu(Menu menu)
    {
        boolean drawerOpen = drawerLo.isDrawerOpen(drList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);   //видима если панель закрыта и наоборот
        return super.onPrepareOptionsMenu(menu);
    }
}

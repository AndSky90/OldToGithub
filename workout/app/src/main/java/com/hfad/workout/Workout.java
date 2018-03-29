package com.hfad.workout;

public class Workout {      //мой класс с данными
    private String name,descr;

    public static final Workout[] wos = {       //wos - массив записей
            new Workout("TLN","5-5-5"),     //workout - одна запись, экземплр
            new Workout("CA","100-100-100"),
            new Workout("TWS","5-10-15"),
            new Workout("SaL","50-21-21")};
    private Workout(String name, String descr){     //конструктор
        this.name=name;
        this.descr=descr;
    }

    public String getName() {
        return name;
    }       //сеттер-геттер

    public String getDescr() {
        return descr;
    }

    public String toString(){
        return this.name;
    }
}

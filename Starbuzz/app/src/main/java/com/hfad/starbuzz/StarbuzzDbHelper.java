package com.hfad.starbuzz;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 3;

    StarbuzzDbHelper(Context context)
    {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override public void onCreate (SQLiteDatabase db){
        updateMyDb(db,0,DB_VERSION);
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDb(db, oldVersion, newVersion);
    }
    private void updateMyDb(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCR TEXT, "
                    + "IMAGE_RES_ID INTEGER);"
            );
            insertDrink(db, "Latte", "Espresso n steamed milk", R.drawable.latte);
            insertDrink(db, "Cappucino", "Espresso n hot milk n foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    private static void insertDrink(SQLiteDatabase db, String name, String descr, int resourceId){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCR", descr);
        drinkValues.put("IMAGE_RES_ID", resourceId);
        db.insert("DRINK",null,drinkValues);
    }
}

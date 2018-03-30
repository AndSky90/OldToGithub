package com.hfad.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the drink from the intent
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        //create cursor
        try {
            SQLiteOpenHelper starbuzzDbHelper = new StarbuzzDbHelper(this);
            SQLiteDatabase db = starbuzzDbHelper.getWritableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCR", "IMAGE_RES_ID", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descrText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3)==1);

                //заполняем представления из бд
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);
                TextView descr = (TextView) findViewById(R.id.description);
                descr.setText(descrText);
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                CheckBox checkBox = findViewById(R.id.checkBox);
                checkBox.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        } catch (SQLException e){
            Toast toast = Toast.makeText(this,"DB unavailable",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onFavoriteClick(View view) {
        int drinkNo=(Integer)getIntent().getExtras().get("drinkNo");
        CheckBox checkBox = findViewById(R.id.checkBox);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", checkBox.isChecked());
        SQLiteOpenHelper starbuzzDbHelper = new StarbuzzDbHelper(DrinkActivity.this);
        try{
            SQLiteDatabase db = starbuzzDbHelper.getWritableDatabase();
            db.update("DRINK", drinkValues, "_id=?",
                    new String[]{Integer.toString(drinkNo)});
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this,"DB unavailable",Toast.LENGTH_LONG);
            toast.show();}
    }
}

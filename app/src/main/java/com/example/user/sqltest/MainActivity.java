package com.example.user.sqltest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv;
    Button btn;
    DBHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dh = new DBHelper(this);
        tv = findViewById(R.id.main_text);
        //tv.setText(dh.getDBDate());
        btn = findViewById(R.id.main_button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dh.getDBDate();
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper (Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "date integer,"
                    + "name text,"
                    + "description text" + ");");

            ContentValues vals = new ContentValues();
//            Date dt;
            vals.put("date", Calendar.getInstance().getTimeInMillis());
            vals.put("name", "Новый топик");
            vals.put("description", "Описание нового топика");
            db.insert("mytable", null, vals );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public String getDBDate() {
            Cursor cursor = getReadableDatabase().rawQuery("select date from mytable", null);

            cursor.moveToFirst();

            long ldt = cursor.getLong(cursor.getColumnIndex("date"));
            Date dt = new Date(ldt);

            Toast.makeText(MainActivity.this, "long = " + dt.toGMTString()//String.valueOf(ldt)
                    , Toast.LENGTH_SHORT).show();
            return "date = ";
            //return "date = " + dt.toString();
        }
    }
}

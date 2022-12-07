package com.vitefinetechapp.vitefinetech.Aeps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "aaa.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tabledata = "create table centercart(piddataxml text,biodata text,biodevice text)";


        db.execSQL(tabledata);

    }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        @Override
        public void onOpen(SQLiteDatabase db){
            super.onOpen(db);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                db.disableWriteAheadLogging();
            }


        }

        public void insetcartdata(String piddataxml,String biodata,String biodevice){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("piddataxml", piddataxml);
            values.put("biodata", biodata);
            values.put("biodevice", biodevice);


            sqLiteDatabase.insert("centercart", null, values);
        }
}

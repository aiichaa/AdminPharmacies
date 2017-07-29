package com.example.info.adminpharmacies;

/**
 * Created by InFo on 13/06/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by InFo on 09/06/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //declare varibale to asigne dababase variables
    private static final String DATABASE_NAME = "pharmacies.db";
    static final String TABLE_NAME = "pharmacies_table";
    static final String COL_1 = "_id";
    static final String COL_2 = "name";
    static final String COL_3 = "LatLng";
    static final String COL_4 = "allNight";



    public DatabaseHelper(Context context ) {
        super(context,DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL("CREATE TABLE pharmacies_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,LatLng TEXT,allNight TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pharmacies_table;");
        onCreate(db);
    }

    public boolean insertData(String name,String LatLng, String allNight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,LatLng);
        contentValues.put(COL_4, allNight);

        long result = db.insert(TABLE_NAME,null,contentValues);//will return -1 if not working
        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public Cursor getAllDate(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME ,null);
        return res;
    }

    public boolean updateDate(String id,String name,String LatLng,String allNight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,LatLng);
        contentValues.put(COL_4, allNight);

        db.update(TABLE_NAME,contentValues,"_id = ?" ,new String[] {id});

        return true;

    }

    public Integer deleteDate(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"_id = ?", new String[]{id});
    }


}

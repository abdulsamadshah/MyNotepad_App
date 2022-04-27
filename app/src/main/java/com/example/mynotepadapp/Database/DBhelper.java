package com.example.mynotepadapp.Database;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mynotepadapp.Models.Models;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {
    //create table
    public static String DBNAME = "mydatabase.db";
    final static int DBVERSION = 1;

    public DBhelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table orders" + "(id Integer primary key autoincrement," +
                        "name text," +
                        "description text"+")");

    }

    //table creat close

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP table if exists orders");
        onCreate(sqLiteDatabase);
    }


    //insert data;
    public boolean insertOrder(String name, String description){

        SQLiteDatabase database=getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put("name",name);
        values.put("description",description);
        long id=database.insert("orders",null,values);
        if (id<=0){
            return true;
        }else{
            return true;
        }

    }
    //insert data close



    //read karne ka code
    public ArrayList<Models> getOrders(){
        ArrayList<Models> orders=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("select name,description from orders",null);
        if (cursor.moveToNext()){
            while (cursor.moveToNext()){
               Models model=new Models("name","description");
                model.setName(cursor.getString(0)+"");
                model.setDescription(cursor.getString(1)+"");

                orders.add(model);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }


    public Cursor getOrderId(int id){
        ArrayList<Models> orders=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from orders where id="+id,null);

        if (cursor !=null)
            cursor.moveToFirst();



        return cursor;
    }



    //Update Data
    public Boolean updateuserdata(String name, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        Cursor cursor = DB.rawQuery("Select * from orders where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("orders", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    //update close

    //Delete Code
    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from orders where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("orders", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
//delete code close
}


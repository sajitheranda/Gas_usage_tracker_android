package com.example.gasweighterlast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBhandler extends SQLiteOpenHelper {
    private static final int version=1;
    private static final String dbname="db1";
    private static final String table="table1";
    private static final String ID="id";
    private static final String TITLE="title";
    private static final String Number="number";

    public DBhandler(@Nullable Context context) {
        super(context,dbname,null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_create_query="CREATE TABLE "
                +table+"("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE+" TEXT,"
                +Number+" TEXT);";


        db.execSQL(table_create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String drop_querry="DROP TABLE IF EXISTS "+table;
        db.execSQL(drop_querry);
        onCreate(db);
    }


    public void Addtodo(TODO todo){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(TITLE,todo.getTitle());
        contentValues.put(Number,todo.getNumber());

        database.insert(table,null,contentValues);

        database.close();
    }

    public int counttodo(){
        SQLiteDatabase  database=getReadableDatabase();
        String get_querrey ="select * from "+table;
        Cursor cursor= database.rawQuery(get_querrey,null);
        return  cursor.getCount();
    }

    public List<TODO> getalltodos(){
        List<TODO> todos=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();

        String get_querry="select * from "+table;
        Cursor cursor=database.rawQuery(get_querry,null);

        if(cursor.moveToFirst()){
            do{
                TODO todo=new TODO();

                todo.setId(cursor.getInt(0));
                todo.setTitle(cursor.getString(1));
                todo.setNumber(cursor.getString(2));

                todos.add(todo);

            }while(cursor.moveToNext());


        }
        database.close();
        return  todos;

    }

    public void deletetodo(int id){
        SQLiteDatabase database=getWritableDatabase();
        String delete_query="delete from "+table+" where "+ID+"="+id+";";
        database.execSQL(delete_query);
        //database.delete(table,"id =?",new String[]{String.valueOf(id)});
        database.close();
    }

    public void updatenewtodo(int id,String title,String description){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(TITLE,title);
        contentValues.put(Number,description);

        database.update(table,contentValues,ID+" =?",new String[]{String.valueOf(id)});
        database.close();

    }



    public TODO getonetodo(int id){
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.query(table,new String[]{ID,TITLE,Number},ID+" = ?",new String[]{String.valueOf(id)},null,null,null,null);

        TODO todo=new TODO();
        if(cursor.moveToFirst()){
            todo.setId(cursor.getInt(0));
            todo.setTitle(cursor.getString(1));
            todo.setNumber(cursor.getString(2));

        }
        System.out.println(todo.getTitle());
        database.close();


        return  todo;
    }


}

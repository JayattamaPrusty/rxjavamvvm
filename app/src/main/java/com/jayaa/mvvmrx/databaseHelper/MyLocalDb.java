package com.jayaa.mvvmrx.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.jayaa.mvvmrx.model.NewsModelItem;

import java.util.ArrayList;

public class MyLocalDb extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "RetrofitMvpDemo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "NEWS";


    public static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "imageurl";
    public static final String COLUMN_MODIFIED_TIME = "modified_time";
    public static final String COLUMN_CREATED_TIME = "created_time";




    private static final String CREATE_TABLE_NEWS = "create table "+TABLE_NAME
            + "("
            + "_id" + " integer primary key autoincrement, "
            + "title" + " text , "
            + "description" + " text , "
            + "imageurl" + " text  "+ ")";



    public MyLocalDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.i("Table created","yes");
    }


    public void insertNewsRecords(String namestr,String titlestr,String contentstr,String url){


        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,titlestr );
        values.put(COLUMN_DESCRIPTION,contentstr );
        values.put(COLUMN_IMAGE,url );
        long k=database.insert(TABLE_NAME, null, values);

        Log.i("insrted",k+"");
        database.close();


    }


    public ArrayList<NewsModelItem> getNewsList(Context context){

        ArrayList<NewsModelItem> newslist=new ArrayList<>();


        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        String SQL_NEWS_QUERY="SELECT * FROM "+TABLE_NAME;

        Cursor cursor=sqLiteDatabase.rawQuery(SQL_NEWS_QUERY,null);


        while (cursor.moveToNext()){

            String title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String dscription=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            String imageurl=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            newslist.add(new NewsModelItem(title,dscription,imageurl));
        }

        cursor.close();

        return newslist;
    }


    public boolean isRowExist(String titlestr, String contentstr, String url){

        boolean status=false;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        String selectFirstQuery = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_TITLE+" = ? ;"/*+" AND "+COLUMN_DESCRIPTION+" = ?"+" AND "+COLUMN_IMAGE+" = ? ;"*/;

        String[] selectionargs=new String[]{titlestr};
        Cursor cursor = sqLiteDatabase.rawQuery(selectFirstQuery,selectionargs);
        int count = cursor.getCount();

        if(count >= 1) {
            status=true;
        }
        cursor.close();
        sqLiteDatabase.close();
        return status;
    }
}

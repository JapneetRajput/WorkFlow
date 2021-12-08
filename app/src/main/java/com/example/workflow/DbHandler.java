package com.example.workflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHandler extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public DbHandler(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE employee (emp_id Integer Primary Key Autoincrement, first_name Text, Surname Text, Username Text, Password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("Drop Table if exists employee");
    }

    public Boolean addEmployee(String Fname, String Sname, String Uname, String Pword){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", Fname);
        values.put("Surname", Sname);
        values.put("Username", Uname);
        values.put("Password", Pword);
        long result = MyDB.insert("employee", null, values);
            if(result==-1) return false;
            else return true;
    }
    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from employee where username = ?", new String[]{username});
        if(cursor.getCount()>0)
            return false;
        else
            return true;
    }
    public Boolean checkuserpass(String username,String Pass){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from employee where Username = ? and Password =?", new String[]{username,Pass});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }
//    public String getName(String username){
//        SQLiteDatabase MyDB = this.getReadableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select first_name from employee where Username = ?" , new String[]{username});
//        final int nameIndex = cursor.getColumnIndex("first_name");
//        String name;
//        do {
//                name = cursor.getString(nameIndex);
//        }while(cursor.moveToNext());
//        return name;
//    }

}

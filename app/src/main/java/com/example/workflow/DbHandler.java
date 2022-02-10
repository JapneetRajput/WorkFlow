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
        MyDB.execSQL("CREATE TABLE employee (emp_id Integer Primary Key Autoincrement, first_name Text, Surname Text, Username Text, Password Text, Email VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("Drop Table if exists employee");
    }

    public Boolean addEmployee(String Fname, String Sname, String Uname, String Pword, String Email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", Fname);
        values.put("Surname", Sname);
        values.put("Username", Uname);
        values.put("Password", Pword);
        values.put("Email", Email);
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

    public String ret_Fname(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select first_name from employee where Username = ?",new String[]{username} );
        StringBuffer fname=new StringBuffer();
        while(cursor.moveToNext()) {
             fname.append(cursor.getString(0));
        }
        return fname.toString();
    }
    public String ret_Lname(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select Surname from employee where Username = ?",new String[]{username} );
        StringBuffer lname=new StringBuffer();
        while(cursor.moveToNext()) {
            lname.append(cursor.getString(0));
        }
        return lname.toString();
    }
    public String ret_em(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select Email from employee where Username = ?",new String[]{username} );
        StringBuffer em=new StringBuffer();
        while(cursor.moveToNext()) {
            em.append(cursor.getString(0));
        }
        return em.toString();
    }
    public String ret_uname(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select Username from employee where Username = ?",new String[]{username} );
        StringBuffer uname=new StringBuffer();
        while(cursor.moveToNext()) {
            uname.append(cursor.getString(0));
        }
        return uname.toString();
    }
    public int ret_id(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select emp_id from employee where Username = ?",new String[]{username} );
        int id=0000;
        while(cursor.moveToNext()) {
            id=cursor.getInt(0);
        }
        return id;
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

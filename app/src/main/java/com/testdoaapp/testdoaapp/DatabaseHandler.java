package com.testdoaapp.testdoaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zealous on 4/7/17.
 */


public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context, Object name, Object factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    String password;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Mydatabase.db";


    // Contacts table namej
    public static final String TABLE_REGISTER = "register";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL_ID = "email_id";
    public static final String KEY_MOB_NO = "mobile_number";
    public static final String KEY_PASSWORD = "password";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_REGISTER + "("
            + KEY_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL ," + " TEXT," + KEY_EMAIL_ID + " TEXT,"
            + KEY_MOB_NO + " TEXT," + KEY_PASSWORD + " TEXT " + ")";

    Registerdata registerdata = new Registerdata();

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);

        // Create tables again
        onCreate(db);

    }

    public Registerdata addregister(Registerdata registerdata)
    // code to add the new register
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL_ID, registerdata.getEmail_id());//register email id
        values.put(KEY_MOB_NO, registerdata.getMobile_number());//register mobile no
        values.put(KEY_PASSWORD, registerdata.getPassword());
        // Inserting Row
        //registerdata.setId();

        db.insert(TABLE_REGISTER, null, values);
        //  String sql = "select * from " + TABLE_REGISTER + " where " + KEY_EMAIL_ID + "='" + registerdata.getEmail_id()+"'";

        Cursor cursor = db.rawQuery("select * from " + TABLE_REGISTER + " where " + KEY_EMAIL_ID + "='" + registerdata.getEmail_id() + "'", null);

        int i = cursor.getCount();
        System.out.println("===========count================" + i);


        if (cursor.moveToFirst()) {
            do {
                System.out.println("=======id============" + cursor.getString(cursor.getColumnIndex(KEY_ID)));

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                registerdata.setId(id);
            }

            while (cursor.moveToNext());

            cursor.close();


        }
        ;
        db.close(); // Closing database connection

        return registerdata;
    }

    //to get all register

    public Registerdata getregister(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM register  WHERE email_id = '" + username + "' and  password = '" + password + "'", null);

        int i = cursor.getCount();
        System.out.println("===========count================" + i);


        if (cursor.moveToFirst()) {
            do {
                System.out.println("=======id============" + cursor.getString(cursor.getColumnIndex(KEY_ID)));

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                registerdata.setId(id);
            }

            while (cursor.moveToNext());

            cursor.close();


        }
        return registerdata;
    }


//userid====username

    public Registerdata getData(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + TABLE_REGISTER + " where " + KEY_ID + "=" + userid;
        //
        Cursor cursor = db.rawQuery(sql, null);
        //cursor.toString();
        Log.d("===cursor", "getData: " + cursor.toString());

        int count = cursor.getCount();
        System.out.println("=====count123====" + count);


        if (cursor.moveToFirst()) {
            do {
                System.out.println("=======id============" + cursor.getString(cursor.getColumnIndex(KEY_ID)));

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));

                String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ID));
                String phoneno = cursor.getString(cursor.getColumnIndex(KEY_MOB_NO));
                registerdata.setId(id);
                registerdata.setMobile_number(phoneno);
                System.out.println("=======phoneno============" + cursor.getString(cursor.getColumnIndex(KEY_MOB_NO)));
                registerdata.setEmail_id(email);// do what ever you want here
            } while (cursor.moveToNext());
        }
        cursor.close();


        return registerdata;
    }


//
//    public void delete_byID(String email){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_REGISTER, KEY_EMAIL_ID+"="+email, null);
//    }


    //2...


    public void removeSingleContact(String title) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE  FROM " + TABLE_REGISTER + " WHERE " + KEY_EMAIL_ID + "= '" + title + "'");

        //Close the database
        database.close();
    }


    //update


    public void update_byID(int id, String phoneno) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOB_NO, phoneno);
        database.update(TABLE_REGISTER, values, "id=" + id, null);


        database.close();


    }


//validation


    public boolean isRegistered(String email) {
        boolean is_registred = false;
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "Select * from " + TABLE_REGISTER + " where " + KEY_EMAIL_ID + " ='" + email + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.getCount() != 0) {
            is_registred = true;
        }
        cursor.close();
        return is_registred;
    }


    //validation1


//    public boolean getDataValidation(String username) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        //String sql = "select * from " + TABLE_REGISTER + " where+ email_id =?";
//        String Query = "Select * from " + TABLE_REGISTER + " where " + KEY_EMAIL_ID + " = " + username;
//
//        Cursor cursor = db.rawQuery(Query, new String[]{username});
//        //cursor.toString();
//        Log.d("===cursor", "getData: " + cursor.toString());
//
//
////        Registerdata registerdata = new Registerdata();
//
//
//        if (cursor.getCount() < 1) {
//
//            return false;
//
//        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {
//
//
//            return true;
////            password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
//
//
//        }
//        return false;
//    }


    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getTableContacts() {
        return TABLE_REGISTER;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }


}

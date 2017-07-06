package com.testdoaapp.testdoaapp;

/**
 * Created by valkeshpatel on 12/6/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_CHECKINTIME = "checkintime";
    private static final String KEY_CHECKOUTTIME = "checkouttime";
    private static final String KEY_CHECKEDLATITUDE = "latitude";
    private static final String KEY_CHECKEDLONGTITUDE = "longtitude";
    private static final String KEY_CHECKDATETIME = "datetime_entry";
    private static final String KEY_CHECKEDISINTIME = "in_time";
    private static final String KEY_ = "datetime_entry";
    private static final String KEY_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CHECKINTIME + " TEXT,"
                + KEY_CHECKOUTTIME + " TEXT,"
                + KEY_CHECKEDLATITUDE + " TEXT,"
                + KEY_CHECKEDLONGTITUDE + " TEXT,"
                + KEY_CHECKDATETIME + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_CHECKEDISINTIME + " INTEGER,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    long addContact(Contact contact) {
        long count;
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime() );
        System.out.println("contact.getPhoneNumber() => " + contact.getPhoneNumber() );

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_CHECKINTIME, contact.get_check_in_time()); // Contact
        values.put(KEY_CHECKOUTTIME, contact.get_check_out_time()); // Contact
        values.put(KEY_CHECKDATETIME, formattedDate); // Contact
        values.put(KEY_ADDRESS, contact.getAddress()); // Contact
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone


        // Inserting Row
        count = db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return count;
    }

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.set_check_in_time(cursor.getString(2));
                contact.set_check_out_time(cursor.getString(3));
                contact.setLatilude(cursor.getString(4));
                contact.setLongtitude(cursor.getString(5));
                contact.set_date_time(cursor.getString(6));
                contact.setAddress(cursor.getString(7));



                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        values.put(KEY_CHECKINTIME, contact.get_check_in_time());
        values.put(KEY_CHECKOUTTIME, contact.get_check_out_time());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
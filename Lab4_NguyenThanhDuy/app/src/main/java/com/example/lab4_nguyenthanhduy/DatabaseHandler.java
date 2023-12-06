package com.example.lab4_nguyenthanhduy;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHandler extends SQLiteOpenHelper {
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
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
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
    // Adding new contact
    public void addContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.toString());
        values.put(KEY_PH_NO, contact.toString());
        sqLiteDatabase.insert(TABLE_CONTACTS, null, values);
    }
    // Getting single contact
//    public Contact getContact(int id) {}
//    // Getting All Contacts
//    public List<Contact> getAllContacts() {}
//    // Updating single contact
//    public int updateContact(Contact contact) {}
//    // Deleting single contact
//    public void deleteContact(Contact contact) {}

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add contacts to the list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1),cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        // Return the contact list
        return contactList;
    }

}

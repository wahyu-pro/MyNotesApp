package com.wahyu.mynotesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class NoteHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static NoteHelper INSTANCE;
    private static SQLiteDatabase database;

    private NoteHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    // inisiasi database
    public static NoteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    // open koneksi database
    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    // close koneksi database
    public void close(){
        databaseHelper.close();

        if (database.isOpen()){
            databaseHelper.close();
        }
    }

    // Crud operation

    // select all
    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC "
        );
    }

    // select by id
    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    // insert data
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    // update data
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    // delete data
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}

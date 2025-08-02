package com.example.expenser;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "expenses";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "expense";

    public ExpenseDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, category TEXT, amount DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertExpense(String title, String category, double amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("category", category);
        values.put("amount", amount);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllExpenses() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
	
	public void deleteExpense(int id) {
    SQLiteDatabase db = getWritableDatabase();
    db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    db.close();
    }

}

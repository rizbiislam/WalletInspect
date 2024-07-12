package com.example.walletinspect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {
    public Databasehelper(@Nullable Context context) {
        super(context, "digital_moneyback", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE expense(id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time INTEGER)");
        db.execSQL("CREATE TABLE income(id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS income");
        onCreate(db);
    }

    public void addExpense(double amount, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        contentValues.put("reason", reason);
        contentValues.put("time", System.currentTimeMillis());

        db.insert("expense", null, contentValues);
        db.close(); // Close the database connection
    }

    public void addIncome(double amount, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        contentValues.put("reason", reason);
        contentValues.put("time", System.currentTimeMillis());

        db.insert("income", null, contentValues);
        db.close(); // Close the database connection
    }

    public double calculateExpense() {
        double totalExpense = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(1);
                totalExpense += amount;
            }
        }

        // Close cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return totalExpense;
    }

    public double calculateIncome() {
        double totalIncome = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM income", null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(1);
                totalIncome += amount;
            }
        }

        // Close cursor and database connection
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return totalIncome;
    }
    public double balence(){
        double totalBalence = 0;

        totalBalence = calculateIncome() - calculateExpense();

        return totalBalence;
    }

    public Cursor Incomealldata(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor11 = db.rawQuery("select * from income",null);;
        return cursor11;
    }

    public Cursor Expensealldata(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor12 = db.rawQuery("select * from expense",null);;
        return cursor12;
    }

}

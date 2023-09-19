package com.salatech.taawuncarinsurance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseInsurance extends SQLiteOpenHelper {
    public static final String databaseName = "Signup.db";


    public DataBaseInsurance(@Nullable Context context) {
        super(context, "Signup.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(name TEXT,email TEXT primary key, password TEXT)");
        MyDatabase.execSQL("create Table insurance_renewals(id INTEGER PRIMARY KEY AUTOINCREMENT,policy_number TEXT," +
                "expiration_date TEXT,renewal_years INTEGER,price REAL,user_email TEXT,FOREIGN KEY(user_email)REFERENCES allusers(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");
        MyDatabase.execSQL("drop Table if exists insurance_renewals");
    }

    public boolean inserData(String name, String email, String password) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allusers", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkname(String name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where name = ?   ", new String[]{name});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where  email = ?  ", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertRenewal(String policyNumber, String expirationDate, String userEmail, double price) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        contentValues.put("policy_number", policyNumber);
        contentValues.put("expiration_date", expirationDate);
        contentValues.put("user_email", userEmail);
        contentValues.put("price", price);
        long result = MyDatabase.insert("insurance_renewals", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getPolicyHolderInfo(String policyNumber, String expirationDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"user_email", "policy_number", "expiration_date"};
        String selection = "policy_number = ? AND expiration_date = ?";
        String[] selectionArgs = {policyNumber, expirationDate};
        Cursor cursor = db.query("insurance_renewals", columns, selection, selectionArgs, null, null, null);

        String policyHolderInfo = "";

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex("user_email"));
            @SuppressLint("Range") String policyNumberResult = cursor.getString(cursor.getColumnIndex("policy_number"));
            @SuppressLint("Range") String expirationDateResult = cursor.getString(cursor.getColumnIndex("expiration_date"));

            policyHolderInfo = "User Email: " + userEmail + "\nPolicy Number: " + policyNumberResult + "\nExpiration Date: " + expirationDateResult;
        }

        cursor.close();
        db.close();
        return policyHolderInfo;
    }

    // Check if the policy holder already has insurance based on policy number and expiration date
    public boolean checkInsurance(String policyNumber, String expirationDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id"};
        String selection = "policy_number = ? AND expiration_date = ?";
        String[] selectionArgs = {policyNumber, expirationDate};
        Cursor cursor = db.query("insurance_renewals", columns, selection, selectionArgs, null, null, null);

        boolean hasInsurance = cursor.getCount() > 0;

        cursor.close();
        db.close();
        return hasInsurance;
    }
    public boolean insertRenewalYears(int renewalYears){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        contentValues.put("renewal_years",renewalYears);
    long result = MyDatabase.insert("insurance_renewals",null,contentValues);
    if (result == -1){
        return false;
    }else {
        return true;
    }
    }
    // You can add the getPolicyHolderInfo method here as previously provided
}

package com.example.budgetingapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.budgetingapp.models.BudgetLine;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "budget.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "budget_lines";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AMOUNT = "amount";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_AMOUNT + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       onCreate(db);
    }
    /*This field adds a new record to the database*/
    public void addLine(String category, String description, String date, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_DATE, date.toString());
        cv.put(COLUMN_AMOUNT, amount.toString());
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Budget line added", Toast.LENGTH_SHORT).show();
        }
    }

    void updateRow(BudgetLine line) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, line.getDescription());
        cv.put(COLUMN_CATEGORY, line.getCategory());
        cv.put(COLUMN_DATE, line.getDate().toString());
        cv.put(COLUMN_AMOUNT, line.getAmount().toString());
        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{String.valueOf(line.getId())});
        if (result == -1) {
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Transaction updated", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteRow(BudgetLine line) {
       SQLiteDatabase db = this.getWritableDatabase();
       long result = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(line.getId())});
       if (result == -1) {
           Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(context, "Transaction deleted", Toast.LENGTH_SHORT).show();
       }
    }
    public ArrayList<BudgetLine> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<BudgetLine> toReturn;
        if(db != null){
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
        }
        try {
            toReturn = parseCursor(cursor);
        } catch(ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
            toReturn = new ArrayList<>();
        }
        return toReturn;
    }
    public BudgetLine findById(int id) {
        BudgetLine toReturn = null;
        String query = "SELECT * FROM " + TABLE_NAME + "WHERE id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
        }
        try {
            ArrayList<BudgetLine> parsedCursor = parseCursor(cursor);
            toReturn = parsedCursor.get(0);
        } catch(ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return toReturn;


    }
    /**
     * This method is used to parse cursor data into an array list to be used by the app
     */
    ArrayList<BudgetLine> parseCursor(Cursor cursor) throws ParseException {
        ArrayList<BudgetLine> parsedData = new ArrayList<>();
        if (cursor.getCount() != 0) {
            do {
                String date;
                String amount;
                // Format SQLite Date entry to Date object
                    date = String.valueOf(cursor.getString(1));
                // Format SqLite Amount entry to BigDecimal
                    amount = String.valueOf(cursor.getString(4));
                // Add new line to budget ArrayList
                parsedData.add(new BudgetLine(cursor.getInt(0), date, cursor.getString(2), cursor.getString(3), amount));
            } while (cursor.moveToNext());
        }
        return parsedData;
    }

    /*I'm using this to pull all transactions*/
    public ArrayList<HashMap<String, String>> printAllTransactionsToString(){
        SQLiteDatabase the_db;
        //print all transactions
        ArrayList<HashMap<String, String>> transactionsList;

        transactionsList = new ArrayList<>();


        the_db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = the_db.rawQuery(selectQuery,null);



        if(cursor.moveToFirst()) {  //automatically move cursor to first row
            do {
                HashMap<String, String> map = new HashMap<>();

                map.put("date", cursor.getString(0));   //move the cursor based on each index
                map.put("description", cursor.getString(1));
                map.put("amount", cursor.getString(2));
                map.put("category", cursor.getString(3));

                transactionsList.add(map);
            }while (cursor.moveToNext());

        }
        return transactionsList; //return all data of dynamic list
    }


}

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
        public void addLine(String category, String description, Date date, BigDecimal amount) {
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

    void updateLine(BudgetLine line) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DESCRIPTION, line.getDescription());
        cv.put(COLUMN_CATEGORY, line.getCategory());
        cv.put(COLUMN_DATE, line.getDate().toString());
        cv.put(COLUMN_AMOUNT, line.getAmount().toString());

    }
    public void deleteLine(BudgetLine line) {

    }
    public ArrayList<BudgetLine> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<BudgetLine> toReturn;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        try {
            toReturn = parseCursor(cursor);
        } catch(ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT);
            toReturn = new ArrayList<>();
        }
        Toast.makeText(context, "All data fetched", Toast.LENGTH_SHORT);
        return toReturn;
    }

    /**
     * This method is used to parse cursor data into an array list to be used by the app
     */
    ArrayList<BudgetLine> parseCursor(Cursor cursor) throws ParseException {
        ArrayList<BudgetLine> parsedData = new ArrayList<>();
        if (cursor.getCount() != 0) {
            do {
                Date date;
                BigDecimal amount;
                // Format SQLite Date entry to Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = (Date)dateFormat.parse(cursor.getString(1));
                // Format SqLite Amount entry to BigDecimal
                    amount = new BigDecimal(cursor.getDouble(4));
                // Add new line to budget ArrayList
                parsedData.add(new BudgetLine(cursor.getInt(0), date, cursor.getString(2), cursor.getString(3), amount));
            } while (cursor.moveToNext());
        }
        return parsedData;
    }
}

package com.example.grasbergerm.project2;

/**
 * Created by grasbergerm on 10/29/15.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 2;
    public static final String QUIZ_TABLE_NAME = "quizes";
    public static final String QUIZ_COL_NAME = "name";
    private static final String STRING_CREATE_QUIZ =
            "CREATE TABLE " + QUIZ_TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZ_COL_NAME + " TEXT);";
    public static final String QUEST_TABLE_NAME = "questions";
    public static final String QUEST_COL_QUEST = "quest";
    public static final String QUEST_COL_TYPE = "type";
    public static final String QUEST_COL_NAME = "name";
    public static final String QUEST_COL_ANS = "answer";
    public static final String QUEST_COL_ANS1 = "answer1";
    public static final String QUEST_COL_ANS2 = "answer2";
    public static final String QUEST_COL_ANS3 = "answer3";
    public static final String QUEST_COL_ANSMUL = "mulanswer";
    private static final String STRING_CREATE_QUEST =
            "CREATE TABLE " + QUEST_TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUEST_COL_TYPE + " TEXT, " + QUEST_COL_NAME + " TEXT, " + QUEST_COL_QUEST + " TEXT, " + QUEST_COL_ANS + " TEXT, " + QUEST_COL_ANS1 + " TEXT, " + QUEST_COL_ANS2 + " TEXT, " + QUEST_COL_ANS3 + " TEXT, " + QUEST_COL_ANSMUL + " TEXT);";
    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE_QUIZ);
        db.execSQL(STRING_CREATE_QUEST);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ QUIZ_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ QUEST_TABLE_NAME);
        onCreate(db);
    }
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + QUIZ_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}
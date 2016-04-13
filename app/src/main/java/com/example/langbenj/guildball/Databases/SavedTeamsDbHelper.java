package com.example.langbenj.guildball.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.langbenj.guildball.Helpers.App;

//Base code here was taken from the online Google Android Dev Documentation

public class SavedTeamsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SavedTeams.db";
    public static final String TABLE_NAME = "teams";
    public static final String COLUMN_NAME_ENTRY_ID = "team_id";
    public static final String COLUMN_NAME_TEAM = "teamname";
    public static final String COLUMN_TEAM = "team";
    public static final String COLUMN_NAME_PLAYER1 = "player1";
    public static final String COLUMN_NAME_PLAYER2 = "player2";
    public static final String COLUMN_NAME_PLAYER3 = "player3";
    public static final String COLUMN_NAME_PLAYER4 = "player4";
    public static final String COLUMN_NAME_PLAYER5 = "player5";
    public static final String COLUMN_NAME_PLAYER6 = "player6";
    public static final String COLUMN_NAME_PLAYER7 = "player7";
    public static final String COLUMN_NAME_PLAYER8 = "player8";
    public static final String COLUMN_NAME_PLAYER9 = "player9";
    public static final String COLUMN_NAME_PLAYER10 = "player10";
    public static final String COLUMN_NAME_PLAYER11 = "player11";
    public static final String COLUMN_NAME_PLAYER12 = "player12";
    public static final String COLUMN_NAME_PLAYER13 = "player13";
    public static final String COLUMN_NAME_PLAYER14 = "player14";
    public static final String COLUMN_NAME_PLAYER15 = "player15";
    public static final String COLUMN_NAME_PLAYER16 = "player16";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TEAM + " TEXT," +
                    COLUMN_TEAM + " TEXT," +
                    COLUMN_NAME_PLAYER1 + " TEXT," +
                    COLUMN_NAME_PLAYER2 + " TEXT," +
                    COLUMN_NAME_PLAYER3 + " TEXT," +
                    COLUMN_NAME_PLAYER4 + " TEXT," +
                    COLUMN_NAME_PLAYER5 + " TEXT," +
                    COLUMN_NAME_PLAYER6 + " TEXT," +
                    COLUMN_NAME_PLAYER7 + " TEXT," +
                    COLUMN_NAME_PLAYER8 + " TEXT," +
                    COLUMN_NAME_PLAYER9 + " TEXT," +
                    COLUMN_NAME_PLAYER10 + " TEXT," +
                    COLUMN_NAME_PLAYER11 + " TEXT," +
                    COLUMN_NAME_PLAYER12 + " TEXT," +
                    COLUMN_NAME_PLAYER13 + " TEXT," +
                    COLUMN_NAME_PLAYER14 + " TEXT," +
                    COLUMN_NAME_PLAYER15 + " TEXT," +
                    COLUMN_NAME_PLAYER16 + " TEXT" +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SavedTeamsDbHelper(Context context) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

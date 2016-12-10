package srvikram13.fitnfun.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import srvikram13.fitnfun.R;

import static com.google.firebase.analytics.FirebaseAnalytics.Param.LEVEL;

/**
 * Created by Vikram on 12/7/2016.
 */
public class AppData extends SQLiteOpenHelper {

    private static final String TAG = "DEBUG: AppData > ";

    public static final int MAX_LEVEL = 3;
    public static final int LEVEL_TIME = 30; // seconds

    private static final String USER_INFO_KEY = "userInfo";
    private static final String USERNAME = "username";
    private static final String USER_EMAIL = "useremail";
    private static final String USER_IMAGE_URL = "userImgURL";

    private static AppData appData;
    private ArrayList<GameLevel> gameLevels = new ArrayList<>();

    private int currentLevel = 0;
    private int currentInstruction = 1;

    //  DB related constants
    private static final String DATABASE_NAME = "fitNFun.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SCORES = "tblScores";
    // Columns in the 'tblScores' table
    public static final String TIME = "time";
    public static final String SCORE = "score";
    public static final String HIGHEST_LEVEL = "level";

    private static Context context;
    public static AppData getInstance(Context ctx) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (appData == null) {
            context = ctx;
            appData = new AppData(context.getApplicationContext());
            SQLiteDatabase db  = appData.getReadableDatabase();
            //appData.onCreate(db);
        }
        return appData;
    }

    public GameLevel getGameLevel() {
        return gameLevels.get(currentLevel);
    }
    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public int getCurrentInstruction() {
        return currentInstruction;
    }
    public void setCurrentInstruction(int inst) {
        currentInstruction = inst;
    }
    private AppData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void loadLevels(Context ctx) {
        AssetManager am = ctx.getAssets();

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            InputStream inputStream = am.open("levels.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        Toast.makeText(ctx, text.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "in onCreate");

        createScoresTable(db);

    }
    public void init() {

        //  LEVEL 1
        ArrayList<Instruction> inst = new ArrayList<>();
        inst.add(new Instruction(Action.SHAKE, 2, 2, "Shake the phone left to right *#* times."));
        inst.add(new Instruction(Action.SHAKE, 3, 3, "Shake the phone left to right *#* times"));
        inst.add(new Instruction(Action.JUMP, 2, 4, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 4, 8, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 5, 10, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right *#* times"));

        gameLevels.add(new GameLevel(inst));

        //  LEVEL 2
        inst = new ArrayList<>();
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 2, 2, "Shake the phone left to right *#* times."));
        inst.add(new Instruction(Action.SHAKE, 3, 3, "Shake the phone left to right *#* times."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right *#* times"));
        inst.add(new Instruction(Action.JUMP, 5, 10, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 7, 14, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right *#* times"));

        gameLevels.add(new GameLevel(inst));

        //  LEVEL 3
        inst = new ArrayList<>();
        inst.add(new Instruction(Action.SHAKE, 3, 3, "Shake the phone left to right *#* times."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right *#* times"));
        inst.add(new Instruction(Action.JUMP, 5, 10, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 7, 14, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air *#* times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right *#* times"));

        gameLevels.add(new GameLevel(inst));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // When Android detects you’re referencing an old database (based on
        // the version number), it will call the onUpgrade( ) method
        deleteAllTablesAndReCreate(db);
    }

    private void deleteAllTablesAndReCreate(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE " + TABLE_SCORES + ";");
        }catch (SQLiteException e) {

        } finally {
            onCreate(db);
        }
    }
    private void createScoresTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SCORES + " ("
                + TIME + " REAL NOT NULL, "
                + SCORE + " INTEGER NOT NULL, "
                + HIGHEST_LEVEL + " INTEGER NOT NULL);");
    }
    public void saveScore(int score, int level) {
        SQLiteDatabase db  = this.getReadableDatabase();
        long currTime = Calendar.getInstance().getTimeInMillis();
        db.execSQL("INSERT INTO " + TABLE_SCORES + " VALUES ('" + currTime + "', " + score + ", " + level + ")");
        db.close();
    }
    public int getLatestScore() {
        String selectQuery = "SELECT * FROM "+TABLE_SCORES+" WHERE "+TIME+" = (SELECT MAX("+TIME+") FROM "+TABLE_SCORES+")";
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        int score = 0;
        if (cursor.moveToLast()) {
             score = cursor.getInt(cursor.getColumnIndex(SCORE));
        }
        cursor.close();
        db.close();
        return score;
    }
    public void clearPastScores() {
        SQLiteDatabase db  = this.getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_SCORES + ";");
        createScoresTable(db);
        db.close();
    }
    private void setItem(String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    private String getItem(String key) {
        SharedPreferences preferences = context.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    public void saveUserInfo(String username, String email, String imgURL){
        setItem(USERNAME, username);
        setItem(USER_EMAIL, email);
        setItem(USER_IMAGE_URL, imgURL);
    }
    public String getUsername() {
        return getItem(USERNAME);
    }
    public String getUserEmail() {
        return getItem(USER_EMAIL);
    }
    public String getUserImageUrl() {
        return getItem(USER_IMAGE_URL);
    }
}


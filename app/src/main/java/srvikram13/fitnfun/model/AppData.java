package srvikram13.fitnfun.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Vikram on 12/7/2016.
 */
public class AppData extends SQLiteOpenHelper {

    private static final String TAG = "DEBUG: AppData > ";

    public static final int MAX_LEVEL = 3;
    public static final int LEVEL_TIME = 60; // seconds

    private static AppData appData;
    private ArrayList<GameLevel> gameLevels = new ArrayList<>();

    private int currentLevel = 1;

    //  DB related constants
    private static final String DATABASE_NAME = "fitNFun.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ALL_TIME = "alltime";
    // Columns in the 'alltime' table
    public static final String ALL_TIME_KEY = "key";
    public static final String ALL_TIME_DISTANCE = "distance";
    public static final String ALL_TIME_TIME = "time";

    public static AppData getInstance(Context ctx) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (appData == null) {
            appData = new AppData(ctx.getApplicationContext());
        }
        return appData;
    }
    public GameLevel getGameLevel() {
        return gameLevels.get(currentLevel);
    }
    public AppData(Context context) {
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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //  LEVEL 1
        ArrayList<Instruction> inst = new ArrayList<>();
        inst.add(new Instruction(Action.SHAKE, 2, 2, "Shake the phone left to right 3 times."));
        inst.add(new Instruction(Action.JUMP, 2, 4, "Jump in the air 3 times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 3, 3, "Shake the phone left to right 3 times"));
        inst.add(new Instruction(Action.JUMP, 4, 8, "Jump in the air 5 times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air 7 times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 5, 10, "Jump in the air 3 times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right 3 times"));

        gameLevels.add(new GameLevel(inst));

        //  LEVEL 2
        inst = new ArrayList<>();
        inst.add(new Instruction(Action.SHAKE, 3, 3, "Shake the phone left to right 3 times."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air 3 times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right 3 times"));
        inst.add(new Instruction(Action.JUMP, 5, 10, "Jump in the air 5 times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 7, 14, "Jump in the air 7 times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air 3 times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right 3 times"));

        gameLevels.add(new GameLevel(inst));

        //  LEVEL 3
        inst = new ArrayList<>();
        inst.add(new Instruction(Action.SHAKE, 3, 3, "Shake the phone left to right 3 times."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air 3 times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right 3 times"));
        inst.add(new Instruction(Action.JUMP, 5, 10, "Jump in the air 5 times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 7, 14, "Jump in the air 7 times with the phone in your hand."));
        inst.add(new Instruction(Action.JUMP, 3, 6, "Jump in the air 3 times with the phone in your hand."));
        inst.add(new Instruction(Action.SHAKE, 5, 5, "Shake the phone left to right 3 times"));

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
            db.execSQL("DROP TABLE " + TABLE_ALL_TIME + ";");
            //db.execSQL("DROP TABLE " + TABLE_STEPS + ";");
//            db.execSQL("DROP TABLE " + TABLE_INFO + ";");
//            db.execSQL("DROP TABLE " + TABLE_WORKOUT_PATH + ";");
        }catch (SQLiteException e) {

        } finally {
            onCreate(db);
        }
    }/*
    public void clearPastScores() {
        SQLiteDatabase db  = this.getReadableDatabase();
        db.execSQL("DROP TABLE " + TABLE_WORKOUT_PATH + ";");

        db.execSQL("CREATE TABLE " + TABLE_WORKOUT_PATH + " ("
                + WORKOUT_PATH_LATITUDE + " REAL NOT NULL, "
                + WORKOUT_PATH_LONGITUDE + " REAL NOT NULL);");

        db.close();
    }*/
}

class GameLevel {
    final ArrayList<Instruction> instructions;

    GameLevel(ArrayList<Instruction> inst) {
        instructions = inst;
    }
}
class Instruction {
    final String description;
    final int count;
    final Action action;
    final int points;
    public Instruction(Action action, int count, int points, String desc) {
        this.action = action;
        this.count = count;
        this.points = points;
        this.description = desc;
    }
}
enum Action {
    JUMP, SHAKE
}
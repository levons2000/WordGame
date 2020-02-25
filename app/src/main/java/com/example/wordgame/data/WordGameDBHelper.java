package com.example.wordgame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.example.wordgame.models.Game;
import com.example.wordgame.models.Word;

import java.util.ArrayList;
import java.util.List;

public class WordGameDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WordsGame.db";
    public static final String SQL_CREATE_WORD_ENTRIES = "CREATE TABLE " + WordTableEntry.TABLE_NAME + " (" +
            WordTableEntry._ID + " INTEGER PRIMARY KEY," +
            WordTableEntry.COLUMN_NAME_WORD + " TEXT," +
            WordTableEntry.COLUMN_NAME_USED + " INTEGER)";

    public static final String SQL_CREATE_GAME_ENTRIES = "CREATE TABLE " + GameTableEntry.TABLE_NAME + " (" +
            GameTableEntry._ID + " INTEGER PRIMARY KEY," +
            GameTableEntry.COLUMN_NAME_SCORE + " INTEGER," +
            GameTableEntry.COLUMN_NAME_RESET_COUNT + " INTEGER," +
            GameTableEntry.COLUMN_NAME_WORD_ENTERED + " INTEGER," +
            GameTableEntry.COLUMN_NAME_BOARD_SIZE + " INTEGER," +
            GameTableEntry.COLUMN_NAME_NUMBER_OF_MINUTES + " INTEGER," +
            GameTableEntry.COLUMN_NAME_BEST_WORD_SCORE + " INTEGER)";


    public static final String SQL_DELETE_WORD_ENTRIES = "DROP TABLE IF EXISTS " + WordTableEntry.TABLE_NAME;

    public static final String SQL_DELETE_GAME_ENTRIES = "DROP TABLE IF EXISTS " + GameTableEntry.TABLE_NAME;

    public static final String SQL_CLEAR_WORD_TABLE = "DELETE FROM " + WordTableEntry.TABLE_NAME;

    public WordGameDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WORD_ENTRIES);
        db.execSQL(SQL_CREATE_GAME_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_WORD_ENTRIES);
        db.execSQL(SQL_DELETE_GAME_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordTableEntry.COLUMN_NAME_WORD, word.getWord());
        values.put(WordTableEntry.COLUMN_NAME_USED, word.getUsed());

        db.insert(WordTableEntry.TABLE_NAME, null, values);
    }

    public void updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordTableEntry.COLUMN_NAME_USED, word.getUsed());

        String selection = WordTableEntry.COLUMN_NAME_WORD + " LIKE ?";
        String[] selectionArgs = {word.getWord()};

        db.update(
                WordTableEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public List<Word> getWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + WordTableEntry.TABLE_NAME,
                null);

        List<Word> words = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Word word = new Word(null, 0);
                word.setWord(cursor.getString(cursor.getColumnIndex(WordTableEntry.COLUMN_NAME_WORD)));
                word.setUsed(cursor.getInt(cursor.getColumnIndex(WordTableEntry.COLUMN_NAME_USED)));

                words.add(word);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return words;
    }

    public void addGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GameTableEntry.COLUMN_NAME_SCORE, game.getScore());
        values.put(GameTableEntry.COLUMN_NAME_RESET_COUNT, game.getResetCount());
        values.put(GameTableEntry.COLUMN_NAME_WORD_ENTERED, game.getWordEntered());
        values.put(GameTableEntry.COLUMN_NAME_BOARD_SIZE, game.getBoardSize());
        values.put(GameTableEntry.COLUMN_NAME_NUMBER_OF_MINUTES, game.getNumberOfMinutes());
        values.put(GameTableEntry.COLUMN_NAME_BEST_WORD_SCORE, game.getBestWordScore());

        db.insert(GameTableEntry.TABLE_NAME, null, values);
    }

    public List<Game> getGames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GameTableEntry.TABLE_NAME,
                null);

        List<Game> games = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Game game = new Game(0,0,0,0,0,0);
                game.setScore(cursor.getInt(cursor.getColumnIndex(GameTableEntry.COLUMN_NAME_SCORE)));
                game.setResetCount(cursor.getInt(cursor.getColumnIndex(GameTableEntry.COLUMN_NAME_RESET_COUNT)));
                game.setWordEntered(cursor.getInt(cursor.getColumnIndex(GameTableEntry.COLUMN_NAME_WORD_ENTERED)));
                game.setBoardSize(cursor.getInt(cursor.getColumnIndex(GameTableEntry.COLUMN_NAME_BOARD_SIZE)));
                game.setNumberOfMinutes(cursor.getInt(cursor.getColumnIndex(GameTableEntry.COLUMN_NAME_NUMBER_OF_MINUTES)));
                game.setBestWordScore(cursor.getInt(cursor.getColumnIndex(GameTableEntry.COLUMN_NAME_BEST_WORD_SCORE)));

                games.add(game);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return games;
    }

    public void clearWords() {
        this.getWritableDatabase().execSQL(SQL_CLEAR_WORD_TABLE);
    }

    public static class WordTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_USED = "used";
    }

    public static class GameTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "games";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_RESET_COUNT = "reset_count";
        public static final String COLUMN_NAME_WORD_ENTERED = "word_entered";
        public static final String COLUMN_NAME_BOARD_SIZE = "board_size";
        public static final String COLUMN_NAME_NUMBER_OF_MINUTES = "number_of_minutes";
        public static final String COLUMN_NAME_BEST_WORD_SCORE = "best_word_score";
    }
}

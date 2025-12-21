package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.util.Comparator;

/* loaded from: classes.dex */
public class DBHelper extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ",";
    public static final String DATABASE_NAME = "saved_recordings.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOG_TAG = "DBHelper";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE saved_recordings (_id INTEGER PRIMARY KEY,recording_name TEXT,file_path TEXT,length INTEGER ,time_added INTEGER )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS saved_recordings";
    private static final String TEXT_TYPE = " TEXT";
    private static OnDatabaseChangedListener mOnDatabaseChangedListener;
    private Context mContext;

    /* loaded from: classes.dex */
    public static abstract class DBHelperItem implements BaseColumns {
        public static final String COLUMN_NAME_RECORDING_FILE_PATH = "file_path";
        public static final String COLUMN_NAME_RECORDING_LENGTH = "length";
        public static final String COLUMN_NAME_RECORDING_NAME = "recording_name";
        public static final String COLUMN_NAME_TIME_ADDED = "time_added";
        public static final String TABLE_NAME = "saved_recordings";
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.mContext = context;
    }

    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener onDatabaseChangedListener) {
        mOnDatabaseChangedListener = onDatabaseChangedListener;
    }

    public RecordingItem getItemAt(int i) {
        Cursor query = getReadableDatabase().query(DBHelperItem.TABLE_NAME, new String[]{"_id", DBHelperItem.COLUMN_NAME_RECORDING_NAME, DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, DBHelperItem.COLUMN_NAME_RECORDING_LENGTH, DBHelperItem.COLUMN_NAME_TIME_ADDED}, null, null, null, null, null);
        if (query.moveToPosition(i)) {
            RecordingItem recordingItem = new RecordingItem();
            recordingItem.setId(query.getInt(query.getColumnIndex("_id")));
            recordingItem.setName(query.getString(query.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_NAME)));
            recordingItem.setFilePath(query.getString(query.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH)));
            recordingItem.setLength(query.getInt(query.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH)));
            recordingItem.setTime(query.getLong(query.getColumnIndex(DBHelperItem.COLUMN_NAME_TIME_ADDED)));
            query.close();
            return recordingItem;
        }
        return null;
    }

    public void removeItemWithId(int i) {
        getWritableDatabase().delete(DBHelperItem.TABLE_NAME, "_ID=?", new String[]{String.valueOf(i)});
    }

    public int getCount() {
        Cursor query = getReadableDatabase().query(DBHelperItem.TABLE_NAME, new String[]{"_id"}, null, null, null, null, null);
        int count = query.getCount();
        query.close();
        return count;
    }

    public Context getContext() {
        return this.mContext;
    }

    public class RecordingComparator implements Comparator<RecordingItem> {
        public RecordingComparator() {
        }

        @Override
        public int compare(RecordingItem recordingItem, RecordingItem recordingItem2) {
            return Long.valueOf(recordingItem2.getTime()).compareTo(Long.valueOf(recordingItem.getTime()));
        }
    }

    public long addRecording(String str, String str2, long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_NAME, str);
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, str2);
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH, Long.valueOf(j));
        contentValues.put(DBHelperItem.COLUMN_NAME_TIME_ADDED, Long.valueOf(System.currentTimeMillis()));
        long insert = writableDatabase.insert(DBHelperItem.TABLE_NAME, null, contentValues);
        OnDatabaseChangedListener onDatabaseChangedListener = mOnDatabaseChangedListener;
        if (onDatabaseChangedListener != null) {
            onDatabaseChangedListener.onNewDatabaseEntryAdded();
        }
        return insert;
    }

    public void renameItem(RecordingItem recordingItem, String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_NAME, str);
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, str2);
        writableDatabase.update(DBHelperItem.TABLE_NAME, contentValues, "_id=" + recordingItem.getId(), null);
        OnDatabaseChangedListener onDatabaseChangedListener = mOnDatabaseChangedListener;
        if (onDatabaseChangedListener != null) {
            onDatabaseChangedListener.onDatabaseEntryRenamed();
        }
    }

    public long restoreRecording(RecordingItem recordingItem) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_NAME, recordingItem.getName());
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, recordingItem.getFilePath());
        contentValues.put(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH, Integer.valueOf(recordingItem.getLength()));
        contentValues.put(DBHelperItem.COLUMN_NAME_TIME_ADDED, Long.valueOf(recordingItem.getTime()));
        contentValues.put("_id", Integer.valueOf(recordingItem.getId()));
        return writableDatabase.insert(DBHelperItem.TABLE_NAME, null, contentValues);
    }
}

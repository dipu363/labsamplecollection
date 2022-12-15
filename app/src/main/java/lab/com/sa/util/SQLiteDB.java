package lab.com.sa.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import lab.com.sa.data.model.TestSampleModel;

public class SQLiteDB extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "labSample.db";

    //  table name
    private static final String TABLE_SAMPLE_COLLECTION = "sampleCollection";


    //  User info Table Columns names
    private static final String SAMPLE_ID = "SAMPLE_ID";
    private static final String LAB_ID = "LAB_ID";
    private static final String PAT_ID = "PAT_ID";
    private static final String PAT_NAME = "PAT_NAME";
    private static final String PAT_PHONE_NO = "PAT_PHONE_NO";
    private static final String PAT_ADDRESS = "PAT_ADDRESS";
    private static final String TEST_NAME = "TEST_NAME";
    private static final String TEST_RESULT = "TEST_RESULT";
    private static final String TEST_STATUS = "TEST_STATUS";


    public static final String CREATE_TABLE_SAMPLE_COLLECTION = "CREATE TABLE " + TABLE_SAMPLE_COLLECTION + "("
            + SAMPLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + LAB_ID + " INTEGER  NOT NULL," + PAT_ID + " TEXT ," + PAT_NAME + " TEXT ," + PAT_PHONE_NO + " TEXT ," + PAT_ADDRESS + " TEXT ,"+ TEST_NAME + " TEXT ," + TEST_RESULT + " TEXT ," + TEST_STATUS + " TEXT " + ")";


    public SQLiteDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SAMPLE_COLLECTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAMPLE_COLLECTION);

        // Create tables again
        onCreate(db);

    }

    public void insertTestSample(TestSampleModel sample) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(LAB_ID, sample.getLab_id());
        initialValues.put(PAT_ID, sample.getPat_id());
        initialValues.put(PAT_NAME, sample.getPat_name());
        initialValues.put(PAT_PHONE_NO, sample.getPat_phone());
        initialValues.put(PAT_ADDRESS, sample.getPat_address());
        initialValues.put(TEST_NAME, sample.getTest_name());
        initialValues.put(TEST_STATUS, sample.getTest_status());
        db.insert(TABLE_SAMPLE_COLLECTION, null, initialValues);
        db.close();

    }

    public void updateTestSample(String status, String tResult, int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(TEST_RESULT, tResult);
        value.put(TEST_STATUS, status);
        db.update(TABLE_SAMPLE_COLLECTION, value, SAMPLE_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();

    }

    public Cursor getAllTestSample(int labid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from sampleCollection  where  LAB_ID = ?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(labid)});
        return c;

    }

    public Cursor getDoneTestSample(int labid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from sampleCollection  where TEST_STATUS = 'Done' and LAB_ID = ?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(labid)});
        return c;

    }public Cursor getTestSampleById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from sampleCollection  where  SAMPLE_ID = ?";
        Cursor c = db.rawQuery(query, new String[]{String.valueOf(id)});
        return c;

    }

    public void deleteSingleSample(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAMPLE_COLLECTION, SAMPLE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }


}

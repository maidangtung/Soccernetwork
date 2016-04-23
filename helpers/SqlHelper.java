package com.maidangtung.soccernetwork.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;


import com.maidangtung.soccernetwork.MainActivity;
import com.maidangtung.soccernetwork.models.Field;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by ZQUANGU112Z on 18-Dec-15.
 */

public class SqlHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "QuanLiSanBong.db";

    //Table name
    private static final String TABLE_NAME = "fields".trim();

    // Fields Table Columns names
    private static final String KEY_ID = "field_id";
    private static final String KEY_NAME = "field_name";
    private static final String KEY_DISTRICT_ID = "district_id";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGTITUDE = "longtitude";


    private Context mContext;
    public static File DATABASE_FILE;
    private boolean mInvalidDatabaseFile = false;
    private boolean mIsUpgraded = false;

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        SQLiteDatabase db = null;

        try {
            db = getReadableDatabase();
            if (db != null) {
                db.close();
            }
            DATABASE_FILE = context.getDatabasePath(DATABASE_NAME);
            if (mInvalidDatabaseFile) {

                copyDatabase();

            }
            if (mIsUpgraded) {
                doUpgrade();
            }
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        mInvalidDatabaseFile = true;
        Log.e("------", "first");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mInvalidDatabaseFile = true;
        mIsUpgraded = true;
    }

    private void doUpgrade() {
        // implement the database upgrade here.
    }


    //để tạo database một lần duy nhất lúc khởi chạy ứng dụng lần đầu
    private void copyDatabase() {

        CopyAsync copyAsync = new CopyAsync();
        copyAsync.execute(mContext);
        setDatabaseVersion();
        mInvalidDatabaseFile = false;
    }

    private void setDatabaseVersion() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_FILE.getAbsolutePath(), null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + DATABASE_VERSION);
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    public ArrayList<Field> getAllRows() {
        ArrayList<Field> mFields = new ArrayList<>();
        Field mField;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            //câu lệnh Query tại đây
            Cursor cursor = db.query(TABLE_NAME, null, null,
                    null, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            //Lấy giá trị tại đây
            while (cursor.isAfterLast() == false) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int district_id = cursor.getInt(2);
                String address = cursor.getString(3);
                float latitude = cursor.getFloat(4);
                float longtitude = cursor.getFloat(5);
                mField = new Field(id, name, district_id, address, latitude, longtitude);
                mFields.add(mField);
                cursor.moveToNext();
            }
            cursor.close();

        } catch (Exception e) {
            Log.e("--------------", e.toString());
            mField = new Field(11, "Error 404", 1, "404", 1,1);
            mFields.add(mField);
        }

        return mFields;
    }

    //count row
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


}

class CopyAsync extends AsyncTask<Context, Void, Void> {
    @Override
    protected void onPreExecute() {
        MainActivity.mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Context... params) {

        AssetManager assetManager = params[0].getResources().getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(SqlHelper.DATABASE_NAME);
            out = new FileOutputStream(SqlHelper.DATABASE_FILE);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(SqlHelper.DATABASE_FILE.getAbsolutePath(), null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + SqlHelper.DATABASE_VERSION);
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.mProgressDialog.dismiss();
        MainActivity.mAdapter.notifyDataSetChanged();
    }
}



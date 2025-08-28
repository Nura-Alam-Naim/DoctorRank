package edu.ewubd.doctorrank223410;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DoctorsDB extends SQLiteOpenHelper {
    public DoctorsDB(Context context) {
        super(context, "Doctors.db", null, 1);
    }

    // ---- DB meta
    private static final String DB_NAME = "doctors.db";
    private static final int DB_VERSION = 1;

    // ---- Table + columns
    private static final String T = "doctors", C_ID = "id", C_NAME = "name", C_SPEC = "speciality", C_RATING = "rating",
            C_ROOM = "roomNo", C_PIC = "picture", C_CHARGE = "charge", C_BDMC = "BDMC",
            C_S1 = "slot1", C_S2 = "slot2", C_S3 = "slot3", C_S4 = "slot4",
            C_S5 = "slot5", C_S6 = "slot6", C_S7 = "slot7", C_S8 = "slot8";


    private static DoctorsDB instance;
    public static synchronized DoctorsDB get(Context ctx) {
        if (instance == null) instance = new DoctorsDB(ctx.getApplicationContext());
        return instance;
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + T + " (" +
                C_ID + " TEXT PRIMARY KEY," +
                C_NAME + " TEXT," +
                C_SPEC + " TEXT," +
                C_RATING + " REAL," +
                C_ROOM + " INTEGER," +
                C_PIC + " TEXT," +
                C_CHARGE + " INTEGER," +
                C_BDMC + " TEXT," +
                C_S1 + " TEXT," + C_S2 + " TEXT," + C_S3 + " TEXT," + C_S4 + " TEXT," +
                C_S5 + " TEXT," + C_S6 + " TEXT," + C_S7 + " TEXT," + C_S8 + " TEXT" +
                ")");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void saveAll(List<T_DoctorInfo> list) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (T_DoctorInfo d : list) {
                ContentValues cv = new ContentValues();
                if (d == null || d.id == null || d.id.isEmpty()) continue;
                cv.put(C_ID, d.id);
                cv.put(C_NAME, d.name);
                cv.put(C_SPEC, d.speciality);
                cv.put(C_RATING, d.rating);
                cv.put(C_ROOM, d.roomNo);
                cv.put(C_PIC, d.picture);
                cv.put(C_CHARGE, d.charge);
                cv.put(C_BDMC, d.BDMC);
                cv.put(C_S1, d.slot1); cv.put(C_S2, d.slot2); cv.put(C_S3, d.slot3); cv.put(C_S4, d.slot4);
                cv.put(C_S5, d.slot5); cv.put(C_S6, d.slot6); cv.put(C_S7, d.slot7); cv.put(C_S8, d.slot8);
                db.insertWithOnConflict(T, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    // Corrected line:
    // In DoctorsDB.java
    public ArrayList<T_DoctorInfo> GetAll() {
        System.out.println("DoctorsDB.GetAll() - Method CALLED.");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null; // Initialize to null
        ArrayList<T_DoctorInfo> out = new ArrayList<>();
        try {
            System.out.println("DoctorsDB.GetAll() - Attempting to query table: " + T);
            c = db.query(T, null, null, null, null, null, C_RATING + " ASC");

            if (c == null) {
                System.err.println("DoctorsDB.GetAll() - Cursor is NULL after query!");
                return out; // Return empty list
            }

            System.out.println("DoctorsDB.GetAll() - Query successful. Cursor row count: " + c.getCount());

            if (c.moveToFirst()) { // Use if instead of while for the first check
                System.out.println("DoctorsDB.GetAll() - moveToFirst() was successful. Processing rows...");
                do {
                    System.out.println("DoctorsDB.GetAll() - Processing a row...");
                    out.add(fromCursor(c));
                } while (c.moveToNext());
                System.out.println("DoctorsDB.GetAll() - Finished processing all rows in cursor.");
            } else {
                System.out.println("DoctorsDB.GetAll() - moveToFirst() returned false. Table is likely empty or query returned no results.");
            }
        } catch (Exception e) {
            System.err.println("DoctorsDB.GetAll() - EXCEPTION during GetAll: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for any exception
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
                System.out.println("DoctorsDB.GetAll() - Cursor closed.");
            }
            // Not closing 'db' here as it's managed by the singleton SQLiteOpenHelper
        }
        System.out.println("DoctorsDB.GetAll() - Returning list with size: " + out.size());
        return out;
    }



    public T_DoctorInfo getById(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(T, null, C_ID + "=?", new String[]{id}, null, null, null);
        try {
            if (c.moveToFirst()) return fromCursor(c);
            return null;
        } finally {
            c.close();
        }
    }
    private ContentValues toValues(T_DoctorInfo d) {
        ContentValues cv = new ContentValues();
        cv.put(C_ID, d.id);
        cv.put(C_NAME, d.name);
        cv.put(C_SPEC, d.speciality);
        cv.put(C_RATING, d.rating);
        cv.put(C_ROOM, d.roomNo);
        cv.put(C_PIC, d.picture);
        cv.put(C_CHARGE, d.charge);
        cv.put(C_BDMC, d.BDMC);
        cv.put(C_S1, d.slot1);
        cv.put(C_S2, d.slot2);
        cv.put(C_S3, d.slot3);
        cv.put(C_S4, d.slot4);
        cv.put(C_S5, d.slot5);
        cv.put(C_S6, d.slot6);
        cv.put(C_S7, d.slot7);
        cv.put(C_S8, d.slot8);
        return cv;
    }
    private T_DoctorInfo fromCursor(Cursor c) {
        T_DoctorInfo d = new T_DoctorInfo();
        d.id = c.getString(c.getColumnIndexOrThrow(C_ID));
        d.name = c.getString(c.getColumnIndexOrThrow(C_NAME));
        d.speciality = c.getString(c.getColumnIndexOrThrow(C_SPEC));
        d.rating = c.getFloat(c.getColumnIndexOrThrow(C_RATING));
        d.roomNo = c.getInt(c.getColumnIndexOrThrow(C_ROOM));
        d.picture = c.getString(c.getColumnIndexOrThrow(C_PIC));
        d.charge = c.getInt(c.getColumnIndexOrThrow(C_CHARGE));
        d.BDMC = c.getString(c.getColumnIndexOrThrow(C_BDMC));
        d.slot1 = c.getString(c.getColumnIndexOrThrow(C_S1));
        d.slot2 = c.getString(c.getColumnIndexOrThrow(C_S2));
        d.slot3 = c.getString(c.getColumnIndexOrThrow(C_S3));
        d.slot4 = c.getString(c.getColumnIndexOrThrow(C_S4));
        d.slot5 = c.getString(c.getColumnIndexOrThrow(C_S5));
        d.slot6 = c.getString(c.getColumnIndexOrThrow(C_S6));
        d.slot7 = c.getString(c.getColumnIndexOrThrow(C_S7));
        d.slot8 = c.getString(c.getColumnIndexOrThrow(C_S8));
        return d;
    }
}

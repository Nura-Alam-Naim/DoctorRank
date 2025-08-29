package edu.ewubd.doctorrank223410;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class DoctorsDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "Doctors.db";
    private static final int DB_VERSION = 2;  // bumped version since schema changed

    // ---- Table + columns
    private static final String T = "doctors";
    private static final String C_ID = "id";
    private static final String C_NAME = "name";
    private static final String C_SPEC = "speciality";
    private static final String C_RATING = "rating";
    private static final String C_ROOM = "roomNo";
    private static final String C_PIC = "picture";
    private static final String C_CHARGE = "charge";
    private static final String C_BDMC = "BDMC";
    private static final String C_SCHEDULE = "schedule"; // NEW JSON column

    private static DoctorsDB instance;
    private static final Gson gson = new Gson();

    public static synchronized DoctorsDB get(Context ctx) {
        if (instance == null) instance = new DoctorsDB(ctx.getApplicationContext());
        return instance;
    }

    private DoctorsDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + T + " (" +
                C_ID + " TEXT PRIMARY KEY," +
                C_NAME + " TEXT," +
                C_SPEC + " TEXT," +
                C_RATING + " REAL," +
                C_ROOM + " INTEGER," +
                C_PIC + " TEXT," +
                C_CHARGE + " INTEGER," +
                C_BDMC + " TEXT," +
                C_SCHEDULE + " TEXT" +    // store schedule as JSON
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T);
        onCreate(db);
    }

    // ---- Save all doctors
    public void saveAll(ArrayList<T_DoctorInfo> doctors) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (T_DoctorInfo d : doctors) {
                if (d == null || d.id == null || d.id.isEmpty()) continue;
                ContentValues cv = new ContentValues();
                cv.put(C_ID, d.id);
                cv.put(C_NAME, d.name);
                cv.put(C_SPEC, d.speciality);
                cv.put(C_RATING, d.rating);
                cv.put(C_ROOM, d.roomNo);
                cv.put(C_PIC, d.picture);
                cv.put(C_CHARGE, d.charge);
                cv.put(C_BDMC, d.BDMC);

                // Convert Map -> JSON
                if (d.schedule != null) {
                    String json = gson.toJson(d.schedule);
                    cv.put(C_SCHEDULE, json);
                } else {
                    cv.put(C_SCHEDULE, "{}");
                }

                db.insertWithOnConflict(T, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void save(T_DoctorInfo d) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
                if (d == null || d.id == null || d.id.isEmpty())
                {
                    return;
                }
                ContentValues cv = new ContentValues();
                cv.put(C_ID, d.id);
                cv.put(C_NAME, d.name);
                cv.put(C_SPEC, d.speciality);
                cv.put(C_RATING, d.rating);
                cv.put(C_ROOM, d.roomNo);
                cv.put(C_PIC, d.picture);
                cv.put(C_CHARGE, d.charge);
                cv.put(C_BDMC, d.BDMC);

                if (d.schedule != null) {
                    String json = gson.toJson(d.schedule);
                    cv.put(C_SCHEDULE, json);
                } else {
                    cv.put(C_SCHEDULE, "{}");
                }
                db.insertWithOnConflict(T, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<T_DoctorInfo> GetAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(T, null, null, null, null, null, C_RATING + " DESC");
        ArrayList<T_DoctorInfo> out = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    out.add(fromCursor(c));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return out;
    }

    // ---- Get doctor by ID
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

    // ---- Clear all doctors
    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(T, null, null);
    }

    // ---- Convert Cursor -> DoctorInfo
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

        // Convert JSON -> Map<String, ArrayList<String>>
        String json = c.getString(c.getColumnIndexOrThrow(C_SCHEDULE));
        if (json != null && !json.isEmpty()) {
            Type type = new TypeToken<Map<String, ArrayList<String>>>() {}.getType();
            d.schedule = gson.fromJson(json, type);
        }
        return d;
    }
}

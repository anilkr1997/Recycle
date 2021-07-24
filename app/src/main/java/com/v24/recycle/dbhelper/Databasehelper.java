package com.v24.recycle.dbhelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.v24.recycle.Modelclass.Stationlist;
import com.v24.recycle.Modelclass.Ticket_list;
import com.v24.recycle.Modelclass.filtermodelclass;
import com.v24.recycle.Modelclass.getUrllist;
import com.v24.recycle.Modelclass.stationfarelist;

import java.util.ArrayList;

public class Databasehelper extends SQLiteOpenHelper {
    private Stationlist stationlist;
    private Ticket_list ticket_list;
    private stationfarelist station_farelist;
    private filtermodelclass filtermodelclasss;
    private getUrllist urllistle;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME_STATION = "Bookinginformation.db";

    //Station list and price database
    private static final String TABLE_NAME_STATION = "StationList";
    private static final String KEY_STATION_ID = "id";
    private static final String KEY_STATIONNAME = "stationname";
    private static final String KEY_FARE = "fare";

    //List of ticket
    private static final String TABLE_NAME_TICKETLIST = "ticketlist";
    private static final String KEY_TL_ID = "id";
    private static final String KEY_S_NO = "ticketsno";
    private static final String KEY_DATE = "ticketdate";
    private static final String KEY_TO = "sto";
    private static final String KEY_FROM = "sfrom";
    private static final String KEY_BARCODE = "sbarcode";
    private static final String KEY_TL_FARE = "sfare";
    private static final String KEY_VALIDE = "svaldate";
    private static final String KEY_VALIDE_UPTO = "svalinupto";

    //Station fare table
    private static final String KEY_TABLE_FARE = "stationfare";
    private static final String KEY_ST_ID = "id";
    private static final String KEY_SFROM_ = "stationfrom";
    private static final String KEY_STO = "stationto";
    private static final String KEY_S_F_T_FARE = "sftfare";

    //url list table
    private static final String KEY_TABLE_URL = "stationeurl";
    private static final String KEY_URL_ID = "id";
    private static final String KEY_SURL_ = "stationurl";
    private static final String KEY_STATION_URL_ID = "stationid_url";
    //ticketserialnumber
    private static final String KEY_TABLE_TICKETSERALNO = "ticketseralnumber";
    private static final String KEY_TSNO_ID = "id";
    private static final String KEY_TSNO = "ticketserialno";

    private static final String CREATE_TABLE_Stationlist = "CREATE TABLE "
            + TABLE_NAME_STATION + "(" + KEY_STATION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STATIONNAME + " TEXT," + KEY_FARE + " TEXT);";


    private static final String CREATE_TABLE_NAME_TICKETLIST = "CREATE TABLE "
            + TABLE_NAME_TICKETLIST + "(" + KEY_TL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_S_NO + " TEXT," + KEY_DATE + " DATETIME," + KEY_TO + " TEXT," + KEY_FROM + " TEXT," + KEY_BARCODE + " TEXT," + KEY_TL_FARE + " TEXT," + KEY_VALIDE + " TEXT," + KEY_VALIDE_UPTO + " TEXT);";


    private static final String CREATE_TABLE_Stationfarelist = "CREATE TABLE "
            + KEY_TABLE_FARE + "(" + KEY_ST_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SFROM_ + " TEXT," + KEY_STO + " TEXT," + KEY_S_F_T_FARE + " TEXT);";

    private static final String CREATE_TABLE_URL_list = "CREATE TABLE "
            + KEY_TABLE_URL + "(" + KEY_URL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SURL_ + " TEXT," + KEY_STATION_URL_ID + " TEXT);";

    private static final String CREATE_TABLE_TSNO = "CREATE TABLE "
            + KEY_TABLE_TICKETSERALNO + "(" + KEY_TSNO_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TSNO + " VARCHAR);";


    private static final String SQL_DELETE_Stationlist =
            "DROP TABLE IF EXISTS " + TABLE_NAME_STATION;
    private static final String SQL_DELETE_TICKET_LIST =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TICKETLIST;
    private static final String SQL_DELETE_FARE_LIST =
            "DROP TABLE IF EXISTS " + KEY_TABLE_FARE;
    private static final String SQL_DELETE_URL_LIST =
            "DROP TABLE IF EXISTS " + KEY_TABLE_URL;

    private static final String SQL_DELETE_TSNO_LIST =
            "DROP TABLE IF EXISTS " + KEY_TABLE_TICKETSERALNO;

    public Databasehelper(@Nullable Context context) {
        super(context, DATABASE_NAME_STATION, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db=SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME_STATION, null, null);
        db.execSQL(CREATE_TABLE_Stationlist);
        db.execSQL(CREATE_TABLE_NAME_TICKETLIST);
        db.execSQL(CREATE_TABLE_Stationfarelist);
        db.execSQL(CREATE_TABLE_URL_list);
        db.execSQL(CREATE_TABLE_TSNO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_Stationlist);
        db.execSQL(SQL_DELETE_TICKET_LIST);
        db.execSQL(SQL_DELETE_FARE_LIST);
        db.execSQL(SQL_DELETE_URL_LIST);
        db.execSQL(SQL_DELETE_TSNO_LIST);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean Station_insert(String STATIONNAME, String FARE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stationname", STATIONNAME);
        contentValues.put("fare", FARE);
        db.insert("StationList", null, contentValues);
        return true;
    }

    public ArrayList<Stationlist> getAllCotacts() {
        ArrayList<Stationlist> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("select * from StationList", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            for (int i = 0; i < res.getColumnCount(); i++) {
                if (res.getString(i) != null && res.getColumnNames() != null) {
                    Log.e("Stationname", res.getColumnName(i) + " : " + res.getString(i));
                    stationlist = new Stationlist(res.getString(res.getColumnIndex(KEY_STATION_ID)), res.getString(res.getColumnIndex(KEY_STATIONNAME)), res.getString(res.getColumnIndex(KEY_FARE)));
                } else {

                }
            }
            array_list.add(stationlist);
            //  array_list.add(res.getString(res.getColumnIndex(KEY_FARE)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean updateStation(Integer id, String STATIONNAME, String FARE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stationname", STATIONNAME);
        contentValues.put("fare", FARE);
        db.update("StationList", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean deleteStation(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("StationList",
                "id = ? ",
                new String[]{Integer.toString(id)});
        return true;
    }


    public boolean Ticket_insert(String KEY_S_NO, String KEY_DATE, String KEY_TO, String KEY_FROM, String KEY_BARCODE, String KEY_TL_FARE, String KEY_VALIDE, String KEY_VALIDE_UPTO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ticketsno", KEY_S_NO);
        contentValues.put("ticketdate", KEY_DATE);
        contentValues.put("sto", KEY_TO);
        contentValues.put("sfrom", KEY_FROM);
        contentValues.put("sbarcode", KEY_BARCODE);
        contentValues.put("sfare", KEY_TL_FARE);
        contentValues.put("svaldate", KEY_VALIDE);
        contentValues.put("svalinupto", KEY_VALIDE_UPTO);


        db.insert("ticketlist", null, contentValues);
        //  Log.e("value",contentValues.getAsString("s_from"));

        return true;
    }

    public ArrayList<Ticket_list> getAllTicket() {
        ArrayList<Ticket_list> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")

        Cursor res = db.rawQuery("select * from ticketlist ORDER BY id DESC", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            for (int i = 0; i < res.getColumnCount(); i++) {
                if (res.getString(i) != null && res.getColumnNames() != null) {
                    Log.e("Stationname", res.getColumnName(i) + " : " + res.getString(i));
                    ticket_list = new Ticket_list(res.getString(res.getColumnIndex(KEY_TL_ID)), res.getString(res.getColumnIndex(KEY_S_NO)), res.getString(res.getColumnIndex(KEY_DATE)), res.getString(res.getColumnIndex(KEY_TO)), res.getString(res.getColumnIndex(KEY_FROM)), res.getString(res.getColumnIndex(KEY_BARCODE)), res.getString(res.getColumnIndex(KEY_TL_FARE)), res.getString(res.getColumnIndex(KEY_VALIDE)), res.getString(res.getColumnIndex(KEY_VALIDE_UPTO)));
                } else {

                }
            }
            array_list.add(ticket_list);
            //  array_list.add(res.getString(res.getColumnIndex(KEY_FARE)));
            res.moveToNext();
        }
        return array_list;
    }


    public ArrayList<filtermodelclass> getAllFilter(String date) {
        ArrayList<filtermodelclass> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("select id, ticketdate, sfare from ticketlist Where date(ticketdate)='" + date + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            for (int i = 0; i < res.getColumnCount(); i++) {
                if (res.getString(i) != null && res.getColumnNames() != null) {
                    Log.e("Stationname", res.getColumnName(i) + " : " + res.getString(i));
                    // array_list.add(res.getColumnName(i) + " : " + res.getString(i));
                    filtermodelclasss = new filtermodelclass(res.getString(res.getColumnIndex(KEY_TL_ID)), res.getString(res.getColumnIndex(KEY_DATE)), res.getString(res.getColumnIndex(KEY_TL_FARE)));
                } else {

                }
            }
            array_list.add(filtermodelclasss);
            //  array_list.add(res.getString(res.getColumnIndex(KEY_FARE)));
            res.moveToNext();
        }
        return array_list;
    }

    public boolean insertfarelist(int S_FROM_ID, int S_TO_ID, String Fare) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stationfrom", S_FROM_ID);
        contentValues.put("stationto", S_TO_ID);
        contentValues.put("sftfare", Fare);
        db.insert(KEY_TABLE_FARE, null, contentValues);
        //  Log.e("value",contentValues.getAsString("s_from"));
        return true;
    }


    public boolean insertuel(String URL,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stationurl", URL);
        contentValues.put("stationid_url", id);
        db.insert(KEY_TABLE_URL, null, contentValues);
        return true;

    }

    public ArrayList<getUrllist> getUrl(int id) {
        ArrayList<getUrllist> urllist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")

        Cursor res = db.rawQuery("select * from stationeurl where stationid_url ='" + id + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
//            for (int i = 0; i < res.getColumnCount(); i++) {
//                if (res.getString(i) != null && res.getColumnNames() != null) {
//                    Log.e("Stationname", res.getColumnName(i) + " : " + res.getString(i));
//                    urllistle = new getUrllist(res.getString(res.getColumnIndex(KEY_URL_ID)), res.getString(res.getColumnIndex(KEY_SURL_)));
//                } else {
//
//                }
//            }
            urllist.add(new getUrllist(res.getString(0), res.getString(1),res.getString(2)));
            //  array_list.add(res.getString(res.getColumnIndex(KEY_FARE)));
            res.moveToNext();
        }
        return urllist;
    }
    public boolean deleteURl(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("stationeurl",
                "id = ? ",
                new String[]{Integer.toString(id)});
        return true;
    }
    public String Fareget(int fromid, int Toid) {
        String fare = "00.00";
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")

        Cursor res = db.rawQuery("select sftfare from stationfare where stationfrom ='" + fromid + "'and stationto ='" + Toid + "' ", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            for (int i = 0; i < res.getColumnCount(); i++) {
                if (res.getString(i) != null && res.getColumnNames() != null) {
                    Log.e("fare", res.getColumnName(i) + " : " + res.getString(i));
                    fare = res.getString(i);

                }
            }
            Log.e("fare123", fare);

            // fare=res.getString();
            //  array_list.add(res.getString(res.getColumnIndex(KEY_FARE)));
            res.moveToNext();
        }
        return fare;
    }


    public boolean checkAlreadyExist(int fromid, int Toid, String str_fare) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")

        Cursor res = db.rawQuery("select stationfrom,stationto from stationfare where stationfrom ='" + fromid + "'and stationto ='" + Toid + "' ", null);
        res.moveToFirst();
        if (res.getCount() > 0) {
            Log.e("exist", res.getColumnName(0));

            return false;
        } else
            //  insertfarelist(fromid,Toid,str_fare);
            return true;
    }

    public ArrayList<stationfarelist> Stationfarelist(int id) {
        String a = null;
        ArrayList<stationfarelist> urllist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if (id == 0) {
            a = "select s.stationname as stationfrom, s1.stationname as stationto, f.sftfare as Fare from stationfare as f inner join StationList as s on s.id=f.stationfrom left join StationList as s1 on s1.id=f.stationto";

        } else {
            a = "select s.stationname as stationfrom, s1.stationname as stationto, f.sftfare as Fare from stationfare as f inner join StationList as s on s.id=f.stationfrom left join StationList as s1 on s1.id=f.stationto where s.id='"+id+"'";

        }
        //"Select post.post_desc from users INNER JOIN post ON users.ID=post.ID WHERE NAME='"+Name+"'" , null
        // Cursor res = db.rawQuery("select * from StationList where stationname like '"+station+"'%", null);
        Cursor res = db.rawQuery(a, null);
        // Cursor res = db.rawQuery("select sta.stationname , fr.stationfrom ,fr.stationto,fr.sftfare from StationList as sta inner join stationfare as fr on sta.id=fr.stationfrom and sta.id=fr.stationto", null);

        res.moveToFirst();
        while (!res.isAfterLast()) {
//            for (int i = 0; i < res.getColumnCount(); i++) {
//                if (res.getString(i) != null && res.getColumnNames() != null) {
//                    Log.e("faretofrom", res.getColumnName(i) + " : " + res.getString(i));
//
//                } else {
//
//                }
//            }
            urllist.add(new stationfarelist(res.getString(0), res.getString(1), res.getString(2)));

            res.moveToNext();

        }
        Log.e("faretofromjson", new Gson().toJson(urllist));

        return urllist;
    }

    public ArrayList<Stationlist> Stationsearch(String station) {
        ArrayList<Stationlist> urllist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        //"Select post.post_desc from users INNER JOIN post ON users.ID=post.ID WHERE NAME='"+Name+"'" , null
                // Cursor res = db.rawQuery("select * from StationList where stationname like '"+station+"'%", null);
                Cursor res = db.rawQuery("select * from StationList where stationname like '" + station + "'%", null);

        res.moveToFirst();
        while (!res.isAfterLast()) {
            for (int i = 0; i < res.getColumnCount(); i++) {
                if (res.getString(i) != null && res.getColumnNames() != null) {
                    Log.e("Stationname", res.getColumnName(i) + " : " + res.getString(i));
                    stationlist = new Stationlist(res.getString(res.getColumnIndex(KEY_STATION_ID)), res.getString(res.getColumnIndex(KEY_STATIONNAME)), res.getString(res.getColumnIndex(KEY_FARE)));
                } else {

                }
            }
            urllist.add(stationlist);
            //  array_list.add(res.getString(res.getColumnIndex(KEY_FARE)));
            res.moveToNext();
        }
        return urllist;
    }

    public void inserttTSNO(String tnsno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ticketserialno", tnsno);
        db.insert(KEY_TABLE_TICKETSERALNO, null, contentValues);
    }

    public String getTSNO() {
        String SNO = "";
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")

        Cursor res = db.rawQuery("select * from ticketseralnumber", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {

            SNO = res.getString(1);
            Log.e("sno", SNO);
            res.moveToNext();
        }
        return SNO;
    }

    public void updateTSNO(int id, String TSNO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ticketserialno", TSNO);

        db.update("ticketseralnumber", contentValues, "id = ? ", new String[]{Integer.toString(id)});
    }
}

package com.trangface.faceai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database  extends SQLiteOpenHelper {
    private   static  final String DATABASE_NAME="FACEAI.sqlite";
    private  static  final String TABLE_FACE_SV="FACESV";
    private  static  final String TABLE_FACE_MAP="MAPFACE";
    private  static  final String TABLE_FACE_DIEMDANH="DIEMDANHSVO";
    private static final String Column_ID="ID";
    private static  final String Column_NAME="NAME";
    private  static final String Column_MASV="MASV";
    private static  final String Column_EYE_XL="EYEXL";
    private static  final String Column_EYE_YL="EYEYL";
    private static  final String Column_EYE_XR="EYEXR";
    private static  final String Column_EYE_YR="EYEYR";
    private static  final String Column_EAR_XL="EARXL";
    private static  final String Column_EAR_YL="EARYL";
    private static  final String Column_EAR_XR="EARXR";
    private static  final String Column_EAR_YR="EARYR";
    private static  final String Column_MOUTH_XL="MOUTHXL";
    private static  final String Column_MOUTH_YL="MOUTHYL";
    private static  final String Column_MOUTH_XR="MOUTHXR";
    private static  final String Column_MOUTH_YR="MOUTHYR";
    private static  final String Column_NOISE_XBASE="NXBASE";
    private static  final String Column_NOISE_YBASE="NYBASE";
    private static  final String Column_NOISE_Right_CheekX="RIGHTX";
    private static  final String Column_NOISE_Right_CheekY="RIGHTY";
    private SQLiteDatabase sqLiteDatabase;



    public Database(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabase=getWritableDatabase();

    }
//    public  void CreatetableMapFACE(){
//        String SQL="CREATE TABLE IF NOT EXISTS "+TABLE_FACE_MAP+" ("+Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
//                " "+Column_MASV+" nvarchar(80),"+Column_NAME+" nvarchar(80),"+Column_EAR_XL+" nvarchar(40), "+Column_EAR_YL+" nvarchar(40)," +
//                ""+Column_EAR_XR+" nvarchar(40), "+Column_EAR_YR+" nvarchar(40),"+Column_EYE_XL+" nvarchar(40), "+Column_EYE_YL+" nvarchar(40)," +
//                " "+Column_EYE_XR+" nvarchar(40),"+Column_EYE_YR+" nvarchar(40),"+Column_MOUTH_XL+" nvarchar(40), "+Column_MOUTH_YL+" nvarchar(40)," +
//                " "+Column_MOUTH_XR+" nvarchar(40),"+Column_MOUTH_YR+","+Column_NOISE_Right_CheekX+" nvarchar(40),"+Column_NOISE_Right_CheekY+" nvarchar(40))  ";
//        sqLiteDatabase.execSQL(SQL);
//    }
    public  void CreateTableSinhvien(){
        String SQL="CREATE TABLE IF NOT EXISTS "+TABLE_FACE_SV+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MASV nvarchar(30),TENSV nvarchar(30)) ";
        sqLiteDatabase.execSQL(SQL);
    }
    public  void CreateTableDiemDanh(){
        String SQL="CREATE TABLE IF NOT EXISTS "+TABLE_FACE_DIEMDANH+"(" +
                " MASV nvarchar(30) PRIMARY KEY,TENSV nvarchar(30),NGAY nvarchar(30) )";
        sqLiteDatabase.execSQL(SQL);
    }
    public  void CreateTableMapFace(){
        String SQL="CREATE TABLE IF NOT EXISTS "+TABLE_FACE_MAP+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " MASV nvarchar(30),TENSV nvarchar(30),WIDTH nvarchar(25),HEIGHT nvarchar(25),EAR_LEFT nvarchar(25), EAR_RIGHT nvarchar(25)," +
                " EYE_LEFT nvarchar(25),EYE_RIGHT nvarchar(25),MOUTH_LEFT nvarchar(25),MOUTH_RIGHT nvarchar(25))";
        sqLiteDatabase.execSQL(SQL);
    }



    public  void AddMapFace(String Masv,String tensv, String postionx,String postiony){
        ContentValues contentValues=new ContentValues();
        contentValues.put("MASV",Masv);contentValues.put("TENSV",tensv);
        contentValues.put("WIDTH",postionx);contentValues.put("HEIGHT",postiony);
//        contentValues.put("EAR_LEFT",ear_left);contentValues.put("EAR_RIGHT",ear_right);
//        contentValues.put("EYE_LEFT",eye_left);contentValues.put("EYE_RIGHT",eye_right);
//        contentValues.put("MOUTH_LEFT",mouth_left);contentValues.put("MOUTH_RIGHT",mouth_right);
        sqLiteDatabase.insert(TABLE_FACE_MAP,null,contentValues);


    }

    public List<String> getUser(String postionx, String postiony) {
        List<String> list=new ArrayList<>();
        sqLiteDatabase=getReadableDatabase();

        String SQL="SELECT * FROM "+TABLE_FACE_MAP+" WHERE WIDTH ='"+postionx+"' AND HEIGHT='"+postiony+"'";
        Cursor cursor=sqLiteDatabase.rawQuery(SQL,null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(1));
            list.add(cursor.getString(2));
        }
        return list;
    }
    public  void AddMapSV(String Masv,String tensv){
        ContentValues contentValues=new ContentValues();
        contentValues.put("MASV",Masv);contentValues.put("TENSV",tensv);
        sqLiteDatabase.insert(TABLE_FACE_SV,null,contentValues);


    }
    public  void AddMapDiemDanh(String Masv,String tensv,String ngay){
        ContentValues contentValues=new ContentValues();
        contentValues.put("MASV",Masv);contentValues.put("TENSV",tensv);
        contentValues.put("NGAY",ngay);;
        sqLiteDatabase.insert(TABLE_FACE_DIEMDANH,null,contentValues);


    }



    public  List<SinhVienModel> getDataList(){
        List<SinhVienModel> list=new ArrayList<>();
        sqLiteDatabase=getReadableDatabase();

        String SQL="SELECT * FROM "+TABLE_FACE_SV;
        Cursor cursor=sqLiteDatabase.rawQuery(SQL,null);
        while (cursor.moveToNext()){
            list.add(new SinhVienModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
        }


        return  list;
    }
    public  List<SinhVienModel> getDataListDiemDanh(String date){
        List<SinhVienModel> list=new ArrayList<>();
        sqLiteDatabase=getReadableDatabase();

        String SQL="SELECT * FROM "+TABLE_FACE_DIEMDANH+" WHERE NGAY ='"+date+"'";
        Cursor cursor=sqLiteDatabase.rawQuery(SQL,null);
        while (cursor.moveToNext()){
            list.add(new SinhVienModel(cursor.getString(1),cursor.getString(2)));
        }


        return  list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int  oldVersion, int  newVersion) {

    }


    public void HandleUpdateMaSVDD(String name, String masv) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        sqLiteDatabase.update(TABLE_FACE_DIEMDANH,contentValues,"Masv='"+masv+"'",null);
    }
}

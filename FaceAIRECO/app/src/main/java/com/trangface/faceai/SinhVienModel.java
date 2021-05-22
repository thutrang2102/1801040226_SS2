package com.trangface.faceai;

import android.content.Context;

public class SinhVienModel {
    private int ID;
    private String Masv;
    private String Namesv;
    private String ngay;
    private String check;
    private Database database;
    public SinhVienModel(Context context){
        database=new Database(context);
        database.CreateTableDiemDanh();
        database.CreateTableMapFace();


    }

    public SinhVienModel(int ID, String masv, String namesv, String ngay, String check) {
        this.ID = ID;
        this.Masv = masv;
        this.Namesv = namesv;
        this.ngay = ngay;
        this.check = check;
    }

    public SinhVienModel( String masv, String namesv) {

        this.Masv = masv;
        this.Namesv = namesv;

    }

    public SinhVienModel(int ID, String masv, String namesv) {
        this.ID = ID;
        Masv = masv;
        Namesv = namesv;
    }

    public  void HandleInsertSinhvien(String masv, String tensv){

    }

    public String getMasv() {
        return Masv;
    }

    public int getID() {
        return ID;
    }

    public String getNamesv() {
        return Namesv;
    }

    public String getNgay() {
        return ngay;
    }

    public String getCheck() {
        return check;
    }
}

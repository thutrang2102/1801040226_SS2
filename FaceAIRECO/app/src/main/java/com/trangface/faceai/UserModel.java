package com.trangface.faceai;

import android.content.Context;

import java.sql.Connection;
import java.util.List;

public class UserModel {

    private  String name;
    private String masv;
    private Database database;
    private String earXL;
    private String earYL;
    private String earXR;
    private String earYR;
    private String eyeXL;
    private String eyeYL;
    private String eyeXR;
    private String eyeYR;
    private String mouthXL;
    private String mouthYL;
    private String mouthXR;
    private String mouthYR;
    private String NoiseX;
    private String NoiseY;
    private String URL="jdbc:mysql://192.168.1.2/face_ai";
    private Connection connection;
    private Context context;
    private ConnectionDB connectionDB;

    public  UserModel(Context context){

        this.context=context;
        database=new Database(context);
        database.CreateTableMapFace();
        database.CreateTableDiemDanh();
        database.CreateTableSinhvien();





    }
    public  UserModel(){

    }

    public UserModel(String name, String masv, String earXL, String earYL) {
        this.name = name;
        this.masv = masv;
        this.earXL = earXL;
        this.earYL = earYL;
    }


    public  void AddMapFace(String Masv, String tensv, String postionx, String postiony){

        database.AddMapFace(Masv,tensv,postionx,postiony);



    }
    public  void AddMapSV(String Masv,String Tensv){
        database.AddMapSV(Masv,Tensv);
    }
        public  List<SinhVienModel> getDataListUser(){
     List<SinhVienModel> list=database.getDataList();
     return  list;
    }
    public  List<SinhVienModel> getDataListDiemDanh(String date){
        List<SinhVienModel> list=database.getDataListDiemDanh(date);
        return  list;
    }
//    public  void AddMapCamera(String Masv,String tensv,String earXL, String earYL, String earXR, String earYR, String eyeXL, String eyeYL, String eyeXR, String eyeYR, String mouthXL, String mouthYL, String mouthXR, String mouthYR,
//                              String NoiseX, String NoiseY){
//     database.AddFaceCamera(Masv,tensv,earXL,earYL,earXR,earYR,eyeXL,eyeYL,eyeXR,eyeYR,mouthXL,mouthYL,mouthXR,mouthYR,
//             NoiseX,NoiseY);
//
//    }
//    public List<String> getDataListUser(String earXL, String earYL, String earXR, String earYR, String eyeXL, String eyeYL, String eyeXR, String eyeYR, String mouthXL, String mouthYL, String mouthXR, String mouthYR,
//                                        String NoiseX, String NoiseY)
//    {
//        List<String> list=database.getDataListUser( earXL,  earYL,  earXR,  earYR,  eyeXL,  eyeYL,  eyeXR,  eyeYR,  mouthXL,  mouthYL,  mouthXR,  mouthYR,
//         NoiseX,  NoiseY);
//        return  list;
//
//
//
//    }
    public List<String> getDataList(String postionx, String postiony){
      List<String> list=database.getUser(postionx,postiony);
      return  list;
    }
    public  void  AddMapDiemDanh(String Masv,String  tensv, String date){
        database.AddMapDiemDanh(Masv,tensv,date);
    }
//


    public String getEarXL() {
        return earXL;
    }

    public String getEarYL() {
        return earYL;
    }

    public String getMasv() {
        return masv;
    }

    public String getName() {
        return name;
    }

    public void HandleUpdateSV(String name, String masv) {
        database.HandleUpdateMaSVDD(name,masv);
    }
}

package com.trangface.faceai;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private Connection connection=null;
    private String URL="jdbc:mysql://192.168.1.2/face_ai";
    public Connection getConnection() {
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(URL,"cuong99","123456");
        } catch (SQLException throwables) {
            Log.d("CHECKED",throwables.getMessage()+"");
        } catch (ClassNotFoundException e) {
            Log.d("CHECKED",e.getMessage());
        }
        return connection;
    }
}

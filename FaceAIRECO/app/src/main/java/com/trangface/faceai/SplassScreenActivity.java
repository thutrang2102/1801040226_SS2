package com.trangface.faceai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplassScreenActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splass_screen);
        SharedPreferences sharedPreferences=getSharedPreferences("INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("ID",0);
        editor.commit();
        UserModel userModel=new UserModel(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             startActivity(new Intent(SplassScreenActivity.this,
                     MenuActivity.class));
            }
        },2500);
    }
}

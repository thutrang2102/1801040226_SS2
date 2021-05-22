package com.trangface.faceai;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class QuanLyActivity  extends AppCompatActivity  {
    private GridView gv;
    private SinhVienAdapter sinhVienAdapter;
    private List<SinhVienModel> arayList;
    private Toolbar toolbar;
    private UserModel userModel;
    private Dialog dialog;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly);
        gv=findViewById(R.id.gv);
        toolbar=findViewById(R.id.toolbar);
        userModel=new UserModel(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Hienthi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dasboard_quanly,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.danhsachlop:
                if(arayList.size()>0){
                    arayList.clear();
                }
                Hienthi();break;
            case R.id.danhsach:
                if(arayList.size()>0){
                    arayList.clear();
                }
                HienthiDiemDanh();break;
            case R.id.them:
                DiaLogAddSv();break;


        }
        return super.onOptionsItemSelected(item);
    }
    public void DiaLogAddSv() {

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_name);
        dialog.show();
        onPause();
        Button btnxacnhan=dialog.findViewById(R.id.btnxacnhan);
        final EditText editmasv=dialog.findViewById(R.id.edtiaMaSV);
        final EditText editensv=dialog.findViewById(R.id.edittensv);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arayList.size()>0){
                    arayList.clear();
                }
                String masv=editmasv.getText().toString().trim();
                String tensv=editensv.getText().toString().trim();
               userModel.AddMapSV(masv,tensv);
                Hienthi();
                dialog.cancel();


            }
        });




    }

    private void Hienthi() {
      arayList=userModel.getDataListUser();
      sinhVienAdapter=new SinhVienAdapter(this,arayList);
      gv.setAdapter(sinhVienAdapter);
    }
    private void HienthiDiemDanh() {

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        arayList=userModel.getDataListDiemDanh(simpleDateFormat.format(calendar.getTime()));
        sinhVienAdapter=new SinhVienAdapter(this,arayList);
        gv.setAdapter(sinhVienAdapter);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               DiaLogUpdate(arayList.get(position).getMasv(),arayList.get(position).getNamesv());
                return true;
            }
        });
    }

    private void DiaLogUpdate(String masv, String namesv) {
       final Calendar calendar=Calendar.getInstance();
        final  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_update);
        dialog.show();;
        final EditText editten=dialog.findViewById(R.id.edittensv);
        final EditText editma=dialog.findViewById(R.id.edtiaMaSV);
        Button btncapnhat=dialog.findViewById(R.id.btncapnhat);
        editma.setText(masv);
        editten.setText(namesv);
        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editten.getText().toString().trim();
                String masv=editma.getText().toString().trim();
                if(name.length()>0){
                    if(masv.length()>0){
                        userModel.HandleUpdateSV(name,masv);
                        if(arayList.size()>0){
                            arayList.clear();
                        }
                        arayList=userModel.getDataListDiemDanh(simpleDateFormat.format(calendar.getTime()));
                        sinhVienAdapter=new SinhVienAdapter(QuanLyActivity.this,arayList);
                        gv.setAdapter(sinhVienAdapter);
                    }
                }else{
                    Toast.makeText(QuanLyActivity.this, "name not null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}

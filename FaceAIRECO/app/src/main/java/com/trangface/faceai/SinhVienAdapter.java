package com.trangface.faceai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SinhVienAdapter  extends BaseAdapter {
    private Context context;
    private List<SinhVienModel> sinhVienModelArrayList;

    public SinhVienAdapter(Context context, List<SinhVienModel> sinhVienModelArrayList) {
        this.context = context;
        this.sinhVienModelArrayList = sinhVienModelArrayList;
    }

    @Override
    public int getCount() {
        return sinhVienModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public  class  ViewHodler{
        TextView txttensv,txtmasv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if(convertView==null){
            convertView= LayoutInflater.from(context)
                    .inflate(R.layout.dong_sv,null);
            viewHodler=new ViewHodler();
            viewHodler.txtmasv=convertView.findViewById(R.id.txtmasv);
            viewHodler.txttensv=convertView.findViewById(R.id.txtensv);
            convertView.setTag(viewHodler);
        }else{
            viewHodler= (ViewHodler) convertView.getTag();
        }
        viewHodler.txttensv.setText(sinhVienModelArrayList.get(position).getNamesv());
        viewHodler.txtmasv.setText(sinhVienModelArrayList.get(position).getMasv());
        return convertView;
    }
}

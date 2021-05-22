package com.trangface.faceai;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.*;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.*;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private Button btncamera;
    private ImageView hinhanh;

    private Canvas canvas;
    private Bitmap bm;
    private FirebaseVisionFaceDetectorOptions options;
    private  FirebaseVisionFaceDetector detector;
    private ImageView cameraKitView;
    private UserModel userModel;
    private Dialog dialog;
    private Button btnxacnhan;
    private EditText editten,editmasv;
    private TextView txttensv,txtmasv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_name);
        userModel=new UserModel(this);
        txtmasv=findViewById(R.id.txtmasv);
        txttensv=findViewById(R.id.txttensv);
        hinhanh = findViewById(R.id.hinhanh);
        btncamera=findViewById(R.id.btncamera);
        editten=dialog.findViewById(R.id.edittensv);
        editmasv=dialog.findViewById(R.id.edtiaMaSV);
        btnxacnhan=dialog.findViewById(R.id.btnxacnhan);



        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckpermissonCamera();
            }
        });

    }


    private void CheckpermissonCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 124);
            } else {
                Camera();
            }
        } else {
            Camera();
        }

    }


    private void Camera() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK){
            FirebaseVisionImage image=null;
            Bitmap bitmap=null;
            try {
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                bitmap=BitmapFactory.decodeStream(inputStream);
                bitmap=bitmap.copy(Bitmap.Config.ARGB_8888,true);
                hinhanh.setImageBitmap(bitmap);
                canvas=new Canvas(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image=FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionFaceDetectorOptions
                    options=new FirebaseVisionFaceDetectorOptions
                    .Builder()
                    .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                    .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                    .enableTracking()
                    .build();
            FirebaseVisionFaceDetector detector=
                    FirebaseVision.getInstance()
                            .getVisionFaceDetector(options);
            Task<List<FirebaseVisionFace>>
                    result
                    =detector.detectInImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {


                            for (FirebaseVisionFace face : firebaseVisionFaces) {
                                final Paint paint=new Paint();
                                paint.setColor(Color.RED);
                                paint.setStyle(Paint.Style.STROKE);
                                paint.setStrokeWidth(2.0f);
                                Rect bounds = face.getBoundingBox();
                                canvas.drawRect(bounds,paint);
                                FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                                FirebaseVisionFaceLandmark rightEar = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
                                FirebaseVisionFaceLandmark  eyeRight = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);
                                FirebaseVisionFaceLandmark eyeLeft = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                                FirebaseVisionFaceLandmark mouthleft = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT);
                                FirebaseVisionFaceLandmark mouthright = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT);
                                FirebaseVisionFaceLandmark Noise   = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE);
                                final String earXL=String.valueOf(leftEar.getPosition().getX().intValue());
                                final String earYL=String.valueOf(leftEar.getPosition().getY().intValue());
                                final String earXR=String.valueOf(rightEar.getPosition().getX().intValue());
                                final String earYR=String.valueOf(rightEar.getPosition().getY().intValue());
                                final String eyeXL=String.valueOf(eyeLeft.getPosition().getX().intValue());
                                final String eyeYL=String.valueOf(eyeLeft.getPosition().getY().intValue());
                                final String eyeXR=String.valueOf(eyeRight.getPosition().getX().intValue());
                                final String eyeYR=String.valueOf(eyeRight.getPosition().getY().intValue());
                                final String mouthXL=String.valueOf(mouthleft.getPosition().getX().intValue());
                                final String mouthYL=String.valueOf(mouthleft.getPosition().getY().intValue());
                                final String mouthXR=String.valueOf(mouthright.getPosition().getX().intValue());
                                final String mouthYR=String.valueOf(mouthright.getPosition().getY().intValue());
                                final String NoiseX=String.valueOf(Noise.getPosition().getX().intValue());
                                final String NoiseY=String.valueOf(Noise.getPosition().getY().intValue());
                                Log.d("CHECKED",NoiseX+" ");

                              dialog.show();
                                btnxacnhan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        
                                       
                                      dialog.cancel();
                                    }
                                });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

    }

}

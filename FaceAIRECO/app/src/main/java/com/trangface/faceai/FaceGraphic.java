package com.trangface.faceai;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;
    private Context context;
    private int k;
    private String name_detect,masv_detect;
    private String status="",left_eye="",right_eye="";

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId,check=1;
    private Dialog dialog;
    private float mFaceHappiness;
    private CameraSourcePreview cameraSourcePreview;
    private CameraSource mCameraSource = null;
    private UserModel userModel;
    private float left;
    private float bottom ;
    private String name="",masv="";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;


    private GraphicOverlay mGraphicOverlay;
    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face,Context context,int k,String name,String masv){
        mFace = face;
        this.k=k;
        this.name=name;
        this.masv=masv;
        this.context=context;
        postInvalidate();
        userModel=new UserModel(context);
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(final Canvas canvas) {
        final Face face = mFace;
        if (face == null) {
            return;
        }


        float a = translateX(face.getPosition().x);
        float b = translateY(face.getPosition().y);
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
        canvas.drawLine(0,0, a, b,mIdPaint);

        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        bottom = y + yOffset;



        final   double roundOffX = Math.round(face.getWidth() * 100.0) / 100.0;
        final double roundOffY = Math.round(face.getHeight() * 100.0) / 100.0;
        canvas.drawLine(1080,0, left, top,mIdPaint);
//        Landmark leftEar = face.getLandmarks().get(Landmark.LEFT_EAR);
//        Landmark rightEar = face.getLandmarks().get(Landmark.RIGHT_EAR);
//        Landmark  eyeRight = face.getLandmarks().get(Landmark.RIGHT_EYE);
//        Landmark eyeLeft = face.getLandmarks().get(Landmark.LEFT_EYE);
//        Landmark mouthleft = face.getLandmarks().get(Landmark.LEFT_MOUTH);
//        Landmark mouthright =face.getLandmarks().get(Landmark.RIGHT_MOUTH);
//        Landmark Noise   = face.getLandmarks().get(Landmark.NOSE_BASE);
//        final String earXL=String.valueOf((int)leftEar.getPosition().x);
//        final String earYL=String.valueOf((int)leftEar.getPosition().y);
//        final String earXR=String.valueOf((int)rightEar.getPosition().x);
//        final String earYR=String.valueOf((int)rightEar.getPosition().y);
//        final String eyeXL=String.valueOf((int)eyeLeft.getPosition().x);
//        final String eyeYL=String.valueOf((int)eyeLeft.getPosition().y);
//        final String eyeXR=String.valueOf((int)eyeRight.getPosition().x);
//        final String eyeYR=String.valueOf((int)eyeRight.getPosition().y);
//        final String mouthXL=String.valueOf((int)mouthleft.getPosition().x);
//        final String mouthYL=String.valueOf((int)mouthleft.getPosition().y);
//        final String mouthXR=String.valueOf((int)mouthright.getPosition().x);
//        final String mouthYR=String.valueOf((int)mouthright.getPosition().y);
//        final String NoiseX=String.valueOf((int)Noise.getPosition().x);
//        final String NoiseY=String.valueOf((int)Noise.getPosition().y);
        canvas.drawRect(left, top, right, bottom, mBoxPaint);

        double  earXL_Face=Math.round(face.getWidth() * 1000.0) / 1000.0;
       double  earYL_Face=Math.round(face.getHeight() * 1000.0) / 1000.0;
//        double dodai_earLeft=Math.round(Math.sqrt(Double.parseDouble(earXL+earYL))*100.0/100.0);
//        double dodai_earRight=Math.round(Math.sqrt(Double.parseDouble(earXR+earYR))*100.0/100.0);
//        double dodai_eyeLeft=Math.round(Math.sqrt(Double.parseDouble(eyeXL+eyeYL))*100.0/100.0);
//        double dodai_eyeRight=Math.round(Math.sqrt(Double.parseDouble(eyeXR+eyeYR))*100.0/100.0);
//        double dodai_mouthLeft=Math.round(Math.sqrt(Double.parseDouble(mouthXL+mouthYL))*100.0/100.0);
//        double dodai_mouthRight=Math.round(Math.sqrt(Double.parseDouble(mouthXR+mouthYR))*100.0/100.0);
        Log.d("CHECKED",earXL_Face+" "+earYL_Face);


        List<String> list=userModel.getDataList(String.valueOf(earXL_Face),String.valueOf(earYL_Face));
   if(list.size()>0){
       name_detect=list.get(1);
       masv_detect=list.get(0);
       try{
           Calendar calendar=Calendar.getInstance();
           SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
           userModel.AddMapDiemDanh(masv_detect,name_detect,simpleDateFormat.format(calendar.getTime()));// ok done

       }catch (Exception e){
           Toast.makeText(context, "checked ! ", Toast.LENGTH_SHORT).show();
       }
   }


        if( k==2&&name.length()>0 && masv.length()>0 ){

            userModel.AddMapFace(masv,name,String.valueOf(earXL_Face),String.valueOf(earYL_Face));

        }

        if(face.getIsSmilingProbability()>0.5){
            status="Smile";
        }else{
            status="normal";
        }
//        if (
//                face.getIsRightEyeOpenProbability() > 0.5) {
//            right_eye="EYE RIGHT OPEN";
//        }else{
//            right_eye="EYE RIGHT CLOSE";
//        }
//        if (
//                face.getIsLeftEyeOpenProbability() > 0.5) {
//            left_eye="EYE LEFT OPEN";
//        }else{
//            right_eye="EYE LEFT CLOSE";
//        }


        canvas.drawText("ID : " + masv_detect, left, bottom + 40.f, mIdPaint);
        canvas.drawText("Name : " + name_detect, left, bottom + 90.f, mIdPaint);
        canvas.drawText("Status: " + status, left, bottom + 130.f, mIdPaint);




    }
    
}







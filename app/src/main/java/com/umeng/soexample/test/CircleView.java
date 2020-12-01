package com.umeng.soexample.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.umeng.soexample.R;
import com.umeng.soexample.utils.ImageLoader;

public class CircleView extends View {


    private Paint paint; //画笔
    private int x,y;
    private int r = 50;

    public CircleView(Context context){
        this(context,null);
    }
    public CircleView(Context context, @Nullable AttributeSet attrs){
        this(context,attrs,0);
    }
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    //Canvas 画纸
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        x = r;
        y = r;
        canvas.drawCircle(x,y,r,paint);
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        paint.setStrokeWidth(4);
        canvas.drawText("hello",20,20,paint);
        final Bitmap bmm = ImageLoader.getIconBitmap(getContext(),R.mipmap.ic_launcher);
        //Bitmap drawable = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        canvas.drawBitmap(bmm,0,0,paint);

        canvas.drawRoundRect(0,100,100,50,5,5,paint);
    }
}

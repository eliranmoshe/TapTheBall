package eliran.taptheball.com.taptheball;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class Ball extends View {

    public Bitmap LargeBallBmp;
    public float currentY=0;
    public float currentX=290;
    Handler handler;
    public float Yping=10;
    public float Xping=-10;
    Context thiscontext;
    Bitmap MiddleBallBmp;
    Bitmap SmallBallBmp;
    public Canvas thiscanvas;
    public int counter=0;
    Paint paint;
    Path path;
    boolean IsInitLine=false;

    public Ball(Context context) {
        super(context);
        thiscontext=context;
        LargeBallBmp = BitmapFactory.decodeResource(getResources(), R.drawable.balltwo);
        MiddleBallBmp=Bitmap.createScaledBitmap(LargeBallBmp,200,200,false);
        SmallBallBmp=Bitmap.createScaledBitmap(LargeBallBmp,100,100,false);



    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitializeBall();
        InitializeLine();

    }
    public void InitializeLine(){
        IsInitLine=true;
        paint= new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path= new Path();
    }
    public void InitializeBall()
    {
        handler = new Handler();
        final Runnable BallFall=new Runnable() {
            @Override
            public void run() {
                currentY=currentY+Yping;
                currentX=currentX+Xping;
                if (counter<10){
                if (currentX==770)
                {
                    Xping=-10;
                }}else if (counter>=10&&counter<20){
                    if (currentX==870)
                    {
                        Xping=-10;
                    }
                }else if (counter>=20)
                {
                    if (currentX==970)
                    {
                        Xping=-10;
                    }
                }
                if (currentY==0)
                {
                    Yping=10;
                }if (currentX==0)
                {
                    Xping=10;
                }
                if (currentY<1685)
                {
                    handler.postDelayed(this,1);

                }else {
                    Intent intent=new Intent("eliran.taptheball.com.taptheball.HANDLER_STOP");
                    LocalBroadcastManager.getInstance(thiscontext).sendBroadcast(intent);
                    handler.removeCallbacks(this);
                    IsInitLine=false;

                }

                //TODO check why on game exist tocuh back press send broadcast

                 invalidate();
            }
        };
        handler.postDelayed(BallFall,1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (IsInitLine) {
            path.moveTo(0, 800);
            path.lineTo(1100, 800);
            canvas.drawPath(path,paint);
        }
        thiscanvas=canvas;
        if (counter<10) {
            canvas.drawBitmap(LargeBallBmp, currentX, currentY, null);
        }else if (counter>=10&&counter<20)
        {
            canvas.drawBitmap(MiddleBallBmp, currentX, currentY, null);
        }else if (counter>=20)
        {
            canvas.drawBitmap(SmallBallBmp, currentX, currentY, null);
        }

    }


}

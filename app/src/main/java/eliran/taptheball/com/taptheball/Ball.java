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
    public float DownYping=18;
    public float DownXping=18;
    public float UpXping=-18;
    public float UpYping=-18;
    float Yping=DownYping;
    float Xping=DownXping;
    Context thiscontext;
    public Bitmap MiddleBallBmp;
    public Bitmap SmallBallBmp;
    public Canvas thiscanvas;
    public int counter=0;
    Paint paint;
    Path path;
    boolean IsInitLine=false;
    public  int maxX=0;
    public int maxY=0;
    public Ball(Context context) {
        super(context);
        //////////initialize the all 3 kind of ball by size
        thiscontext=context;
        LargeBallBmp = BitmapFactory.decodeResource(getResources(), R.drawable.balltwo);
        int a=LargeBallBmp.getWidth();
        MiddleBallBmp=Bitmap.createScaledBitmap(LargeBallBmp,LargeBallBmp.getWidth()*3/4,LargeBallBmp.getHeight()*3/4,false);
        SmallBallBmp=Bitmap.createScaledBitmap(MiddleBallBmp,MiddleBallBmp.getWidth()*3/4,MiddleBallBmp.getHeight()*3/4,false);



    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitializeBall();
        InitializeLine();

    }
    public void InitializeLine(){
        ////////////set the limit line that the user can touch the ball
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
        ///////////start the ball movement
        handler = new Handler();
        final Runnable BallFall=new Runnable() {
            @Override
            public void run() {

                currentY=currentY+Yping;
                currentX=currentX+Xping;
                if (counter<10){
                if (currentX>(maxX-LargeBallBmp.getWidth()))
                {
                    Xping=UpXping;
                }}else if (counter>=10&&counter<20){
                    if (currentX>(maxX-MiddleBallBmp.getWidth()))
                    {
                        Xping=UpXping;
                    }}else if (counter>=20) {
                    if (currentX>(maxX-SmallBallBmp.getWidth()))
                    {
                        Xping=UpXping;
                    }
                }
                if (currentY<0)
                {
                    Yping=DownYping;
                }if (currentX<0)
                {
                    Xping=DownXping;
                }
                if (currentY<maxY)
                {
                    handler.postDelayed(this,1);

                }else {
                    ////////////if the ball falls from the ended Y the handler is stop and send broadcast
                    Intent intent=new Intent("eliran.taptheball.com.taptheball.HANDLER_STOP");
                    LocalBroadcastManager.getInstance(thiscontext).sendBroadcast(intent);
                    handler.removeCallbacks(this);
                    IsInitLine=false;

                }

                //TODO check why on game exist tocuh back press send broadcast

                 invalidate();
            }
        };
        Xping=DownXping;
        Yping=DownYping;
        handler.postDelayed(BallFall,1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ////////////draw the custom ball view all the time that the handler is running
        if (IsInitLine) {
            path.moveTo(0, (maxY/2));
            path.lineTo(maxX, (maxY/2));
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

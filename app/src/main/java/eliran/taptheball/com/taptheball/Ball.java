package eliran.taptheball.com.taptheball;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


public class Ball extends View {

    Bitmap BallBmp;
    public float currentY=0;
    public float currentX=290;
    Handler handler;
    public float Yping=10;
    public float Xping=10;
    Context thiscontext;

    public Ball(Context context) {
        super(context);
        thiscontext=context;
        IitializeBall();
    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        IitializeBall();
    }

    public void IitializeBall()
    {
        BallBmp = BitmapFactory.decodeResource(getResources(), R.drawable.balltwo);
        handler = new Handler();
        final Runnable BallFall=new Runnable() {
            @Override
            public void run() {
                currentY=currentY+Yping;
                currentX=currentX+Xping;
                if (currentX==770)
                {
                    Xping=-10;
                }if (currentY==0)
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
                }

                //TODO if touch the left and right wall set ping to -X |+X
                //TODO if touch the top and bottom wall set ping to -Y|+Y
                 invalidate();
            }
        };
        handler.postDelayed(BallFall,1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(BallBmp,currentX,currentY,null);

    }


}

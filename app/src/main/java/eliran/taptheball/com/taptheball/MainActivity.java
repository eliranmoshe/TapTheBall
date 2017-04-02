package eliran.taptheball.com.taptheball;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    Ball ball;
    TextView currentXYTV;
    int counter = 0;
    FrameLayout FullFrameLauout;
    StartFragment startfragment;
    SharedPreferences preference;
    int maxY;
    int maxX;
  //  private RotateAnimation rotate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-6655727907980016/1019147687");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;
       /* rotate = new RotateAnimation(0f, 360f, Animation.ABSOLUTE,
                0.5f, Animation.ABSOLUTE, 0.5f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(1500);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);*/
        IntentFilter Handlerfilter = new IntentFilter("eliran.taptheball.com.taptheball.HANDLER_STOP");
        IntentFilter Startfilter = new IntentFilter("eliran.taptheball.com.taptheball.GAME_BEGIN");
        HandlerListener hendlerlistener=new HandlerListener();
        LocalBroadcastManager.getInstance(this).registerReceiver(hendlerlistener,Handlerfilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(hendlerlistener,Startfilter);
        preference= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        startfragment=new StartFragment();
        getFragmentManager().beginTransaction().replace(R.id.FullFrameLauout,startfragment).commit();
        currentXYTV = (TextView) findViewById(R.id.CurrentXYTV);
        currentXYTV.setVisibility(View.INVISIBLE);
        //StartBtn = (Button) findViewById(R.id.StartBtn);
       FullFrameLauout = (FrameLayout) findViewById(R.id.FullFrameLauout);



                //ball = (Ball) findViewById(R.id.TheBallView);
        ball = new Ball(MainActivity.this);
       // ball.startAnimation(rotate);
        FullFrameLauout.addView(ball);
        ball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getY() >= (maxY/2)&&ball.DownYping==ball.Yping){

                        if (counter < 10) {
                            if ((event.getY() - ball.currentY) < ball.LargeBallBmp.getHeight() && (event.getY() - ball.currentY) > 0) {
                                float a=event.getX() - ball.currentX;
                                float b=ball.LargeBallBmp.getWidth();
                                float sum=b-a;
                                if ((event.getX() - ball.currentX) < ball.LargeBallBmp.getWidth() && (event.getX() - ball.currentX) > 0) {
                                    if (sum>=ball.LargeBallBmp.getWidth()/2) {
                                        ball.Xping = ball.DownXping;
                                    }else{
                                        ball.Xping=ball.UpXping;
                                    }
                                    ball.Yping = ball.UpYping;
                                    counter = counter + 1;
                                    ball.counter = ball.counter + 1;
                                    currentXYTV.setText("" + counter);
                                }
                            }
                        } else if (counter >= 10&&counter<20) {
                            if ((event.getY() - ball.currentY) < ball.MiddleBallBmp.getHeight() && (event.getY() - ball.currentY) > 0) {
                                if ((event.getX() - ball.currentX) < ball.MiddleBallBmp.getWidth() && (event.getX() - ball.currentX) > 0) {
                                    if (ball.MiddleBallBmp.getWidth()/2>(event.getX() - ball.currentX)) {
                                        ball.Xping = ball.DownXping;
                                    }else {
                                        ball.Xping=ball.UpXping;
                                    }
                                    ball.Yping= ball.UpYping;
                                    counter = counter + 1;
                                    ball.counter = ball.counter + 1;
                                    currentXYTV.setText("" + counter);
                                }
                            }
                        } else if (counter >= 20) {
                            if ((event.getY() - ball.currentY) < ball.SmallBallBmp.getHeight() && (event.getY() - ball.currentY) > 0) {
                                if ((event.getX() - ball.currentX) < ball.SmallBallBmp.getWidth() && (event.getX() - ball.currentX) > 0) {
                                    if (ball.SmallBallBmp.getWidth()/2>=(event.getX() - ball.currentX)){
                                    ball.Xping =  ball.DownXping;}else { ball.Xping=ball.UpXping;}
                                    ball.Yping= ball.UpYping;
                                    counter = counter + 1;
                                    ball.counter = ball.counter + 1;
                                    currentXYTV.setText("" + counter);
                                }
                            }
                        }
                }
                }
                return true;
            }
        });
    }

    class HandlerListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("eliran.taptheball.com.taptheball.HANDLER_STOP")) {
                int top= preference.getInt("top score",counter);
                if (counter>=top) {
                    preference.edit().putInt("top score",counter).commit();
                }
                currentXYTV.setText("0");
                counter = 0;
                ball = new Ball(MainActivity.this);
                FullFrameLauout.addView(ball);
                getFragmentManager().beginTransaction().replace(R.id.FullFrameLauout,startfragment).commit();
            }if (intent.getAction().equals("eliran.taptheball.com.taptheball.GAME_BEGIN")){
                if (maxX<700&&maxY<1000){
                    ball.DownXping=8;
                    ball.DownYping=8;
                    ball.UpXping=-8;
                    ball.UpYping=-8;
                }
                ball.maxX=maxX;
                ball.maxY=maxY;
                ball.InitializeBall();
                ball.InitializeLine();
                currentXYTV.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

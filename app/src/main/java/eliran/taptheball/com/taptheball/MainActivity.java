package eliran.taptheball.com.taptheball;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Ball ball;
    TextView currentXYTV;
    int counter = 0;
    Button StartBtn;
    FrameLayout FullFrameLauout;
    StartFragment startfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter Handlerfilter = new IntentFilter("eliran.taptheball.com.taptheball.HANDLER_STOP");
        IntentFilter Startfilter = new IntentFilter("eliran.taptheball.com.taptheball.GAME_BEGIN");
        HandlerListener hendlerlistener=new HandlerListener();
        LocalBroadcastManager.getInstance(this).registerReceiver(hendlerlistener,Handlerfilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(hendlerlistener,Startfilter);
        startfragment=new StartFragment();
        getFragmentManager().beginTransaction().replace(R.id.FullFrameLauout,startfragment).commit();
        currentXYTV = (TextView) findViewById(R.id.CurrentXYTV);
        currentXYTV.setVisibility(View.INVISIBLE);
        //StartBtn = (Button) findViewById(R.id.StartBtn);
       FullFrameLauout = (FrameLayout) findViewById(R.id.FullFrameLauout);



                //ball = (Ball) findViewById(R.id.TheBallView);
        ball = new Ball(MainActivity.this);
        FullFrameLauout.addView(ball);
        ball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ball.Xping = 10;
                    ball.Yping = -10;
                    counter = counter + 1;
                    currentXYTV.setText("" + counter);
                }
                return true;
            }
        });
    }

    class HandlerListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("eliran.taptheball.com.taptheball.HANDLER_STOP")) {
                currentXYTV.setText("0");
                counter = 0;
                ball = new Ball(MainActivity.this);
                FullFrameLauout.addView(ball);
                getFragmentManager().beginTransaction().replace(R.id.FullFrameLauout,startfragment).commit();
            }if (intent.getAction().equals("eliran.taptheball.com.taptheball.GAME_BEGIN")){

                ball.IitializeBall();
                currentXYTV.setVisibility(View.VISIBLE);
            }
        }
    }
}

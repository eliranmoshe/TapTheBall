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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentfilter = new IntentFilter("eliran.taptheball.com.taptheball.HANDLER_STOP");
        LocalBroadcastManager.getInstance(this).registerReceiver(new HandlerListener(), intentfilter);
        StartBtn = (Button) findViewById(R.id.StartBtn);
        FullFrameLauout = (FrameLayout) findViewById(R.id.FullFrameLauout);
        currentXYTV = (TextView) findViewById(R.id.CurrentXYTV);
        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                ball = new Ball(MainActivity.this);
                FullFrameLauout.addView(ball);
                //ball = (Ball) findViewById(R.id.TheBallView);
                ball.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            if ((event.getY() - ball.currentY) > 250 && (event.getY() - ball.currentY) < 330) {
                                if ((event.getX() - ball.currentX) > 100 && (event.getX() - ball.currentX) < 200) {
                                    ball.Xping = 10;
                                    ball.Yping = -10;
                                    counter = counter + 1;
                                    currentXYTV.setText("" + counter);
                                }
                            }
                        }
                        return true;
                    }
                });
            }
        });
    }

    class HandlerListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            StartBtn.setVisibility(View.VISIBLE);
            currentXYTV.setText("0");
            counter=0;
        }
    }
}

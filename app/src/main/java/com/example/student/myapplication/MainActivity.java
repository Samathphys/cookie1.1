package com.example.student.myapplication;

import android.annotation.SuppressLint;
import android.content.AbstractThreadedSyncAdapter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView cookie;
    TextView n, sec, c, tv1, tv2, tv3, tv4, tv5;
    TextView [] array = new TextView[5];
    Button click, farm;
    int num = 0, count = 1, speed = 0;
    Handler h;

    int i = 0;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cookie = findViewById(R.id.cookie);
        n = findViewById(R.id.n);
        sec = findViewById(R.id.sec);
        click = findViewById(R.id.click);
        farm = findViewById(R.id.farm);
        c = findViewById(R.id.count);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        array[0] = tv1;
        array[1] = tv2;
        array[2] = tv3;
        array[3] = tv4;
        array[4] = tv5;


        cookie.setOnClickListener(this);
        click.setOnClickListener(this);
        farm.setOnClickListener(this);

        Thread f = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        num += speed;
                        h.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        f.start();

        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 0:
                        n.setText(num + " cookies");
                        break;
                    case 1:
                        int rand = (int)msg.obj;
                        array[rand].setText("");
                }

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cookie:
                num+=count;
                n.setText(num + " cookies");



                final int rand = i++;
                if(i == 5) i = 0;

                array[rand].setText("+" + count);

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            h.sendMessage(h.obtainMessage(1, rand));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();

                break;
            case R.id.click:
                if(num < 10) return;
                num -= 10;
                count += 1;
                c.setText(count + " cookies per tap");
                break;
            case R.id.farm:
                if(num < 10) return;
                num -= 10;
                speed += 1;
                sec.setText(speed + " cookies per sec");
                break;
        }
    }
}

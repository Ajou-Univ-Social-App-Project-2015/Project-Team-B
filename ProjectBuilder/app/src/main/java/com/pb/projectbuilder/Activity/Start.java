package com.pb.projectbuilder.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pb.projectbuilder.R;

public class Start extends AppCompatActivity {

    int splashSceneNumber;

    LinearLayout splashLayout;

    Handler mHandler;

    boolean isClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // TODO Auto-generated method stub

        // xml 소스 참조
        splashLayout = (LinearLayout) findViewById(R.id.splashLayout);

        // 처음화면 0
        splashSceneNumber = 0;

        // 클릭 이벤트가 있었는지 확인
        isClick = true;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (splashSceneNumber) {
                    case 0:
                        // 두번째 화면
                        splashSceneNumber = 1;
                        mHandler.sendEmptyMessage(splashSceneNumber);
                        break;

                    case 1:
                        splashSceneNumber = 2;
                        mHandler.sendEmptyMessageDelayed(splashSceneNumber, 1000);
                        break;

                    case 2:
                        // 엑티비티 종료
                        Start.this.finish();
                        break;

                    case 3:
                        // 딜레이이벤트 클리기 없을경우 바로 0 이벤트로 보낸다..
                        if (isClick && splashSceneNumber == 0) {
                            splashSceneNumber = 0;
                            mHandler.sendEmptyMessage(splashSceneNumber);
                        }
                        break;
                }
            }
        };
        mHandler.sendEmptyMessageDelayed(3,1000);
    }

    public void hn_splashOnclick(View v) {

        switch (splashSceneNumber) {
            case 0:
                splashSceneNumber = 0;

                isClick = false;
                mHandler.sendEmptyMessage(splashSceneNumber);
                break;

            case 1:
                splashSceneNumber = 2;
                mHandler.sendEmptyMessage(splashSceneNumber);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

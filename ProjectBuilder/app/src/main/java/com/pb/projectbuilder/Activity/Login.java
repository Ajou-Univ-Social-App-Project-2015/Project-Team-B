package com.pb.projectbuilder.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.HttpClient;
import com.pb.projectbuilder.R;

import org.apache.http.Header;

public class Login extends Activity {
    EditText email;
    EditText passwd;
    Button login;
    Button signup;
    TextView test;
    TextView rep;
    String TAG = "Log";

    private static final String TYPEFACE_NAME = "YoonGothic-720.ttf";
    private Typeface typeface = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //loadTypeface();
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.email);
        passwd = (EditText) findViewById(R.id.passwd);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {


                                         RequestParams params = new RequestParams();
                                         params.put("email", email.getText().toString().trim());
                                         params.put("passwd", passwd.getText().toString().trim());


                                         HttpClient.post("login", params, new AsyncHttpResponseHandler() {
                                             @Override
                                             public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                                 String res = new String(bytes);
                                                 Log.d(TAG, "Http GET Success " + res);

                                                 if (res.equals("success")) {
                                                     Intent intent = new Intent(Login.this, ProjectList.class);
                                                     startActivity(intent);


                                                 }
                                             }

                                             @Override
                                             public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                             }
                                         });
                                     }
                                 }
            );
            signup=(Button) findViewById(R.id.signup);

            signup.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
            }

            );


        }


    private void loadTypeface(){
        if(typeface==null)
            try {
                typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);
            }catch (Exception e){

            }
    }

    @Override
    public void setContentView(int viewId) {
        View view = LayoutInflater.from(this).inflate(viewId, null);
        ViewGroup group = (ViewGroup)view;
        int childCnt = group.getChildCount();
        for(int i=0; i<childCnt; i++){
            View v = group.getChildAt(i);
            if(v instanceof TextView){
                ((TextView)v).setTypeface(typeface);
            }
        }
        super.setContentView(view);
    }



        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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


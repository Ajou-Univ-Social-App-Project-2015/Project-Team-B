package com.pb.projectbuilder.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;

import org.apache.http.Header;

/**
 * Created by sanghee on 2015-11-07.
 */
public class AddProject extends AppCompatActivity {
    Button btnSave;
    EditText p_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        btnSave=(Button) findViewById(R.id.add_todo_button);
        p_name=(EditText) findViewById(R.id.add_pj_name);

        /* 저장 버튼 */
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (p_name.getText().toString().equals("")) {
                    Toast.makeText(AddProject.this, "please input!!", Toast.LENGTH_SHORT).show();
                } else{
                    RequestParams params = new RequestParams();
                    params.put("p_name", p_name.getText().toString().trim());


                    HttpClient.post("addproject", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            Log.d("AddProject", "Http POST Success ");
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Log.d("AddProject", "Http POST Fail ");

                        }
                    });
                    finish();
                }
            }
        });
    }

}
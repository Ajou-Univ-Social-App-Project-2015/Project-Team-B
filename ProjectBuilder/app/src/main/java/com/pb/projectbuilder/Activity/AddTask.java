package com.pb.projectbuilder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;

import org.apache.http.Header;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        final EditText t_name = (EditText)findViewById(R.id.taskname);
        final EditText descript = (EditText)findViewById(R.id.desc);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.due_date);

        Button register = (Button)findViewById(R.id.task_add_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("t_name", t_name.getText().toString().trim());
                params.put("passwd", descript.getText().toString().trim());
                params.put("due_date", datePicker.toString().trim());

                HttpClient.get("aadtask", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            finish();
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }
        });


    }
}

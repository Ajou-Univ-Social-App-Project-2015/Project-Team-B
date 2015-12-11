package com.pb.projectbuilder.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Task;

import org.apache.http.Header;

import java.io.Serializable;
import java.sql.Date;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        Toolbar toolbar = (Toolbar) findViewById(R.id.a_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Task");


        final EditText t_name = (EditText)findViewById(R.id.taskname);
        final EditText descript = (EditText)findViewById(R.id.desc);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.due_date);

        Button register = (Button)findViewById(R.id.task_add_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.setT_name(t_name.getText().toString());
                task.setDescript(descript.getText().toString().trim());
                task.setDue_date(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());

                RequestParams params = new RequestParams();
                params.put("t_name", task.getT_name());
                params.put("descript", task.getDescript());
                params.put("due_date", task.getDue_date());

                HttpClient.get("addtask", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        finish();
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
                Intent intent = new Intent(AddTask.this, ProjectMain.class);
                intent.putExtra("task", task);

            }
        });


    }
}

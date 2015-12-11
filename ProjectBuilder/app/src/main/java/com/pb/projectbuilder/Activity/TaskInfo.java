package com.pb.projectbuilder.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskInfo extends ActionBarActivity {
    private String t_name;
    private int t_num;
    private String desc;
    private String due_date;
    private int finish;


    public TaskInfo() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskinfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.t_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        t_name = intent.getExtras().getString("t_name");
        desc = intent.getExtras().getString("descript");
        due_date = intent.getExtras().getString("due_date");
        finish = intent.getExtras().getInt("finish");
        t_num =intent.getExtras().getInt("t_num");
        getSupportActionBar().setTitle(t_name);


        TextView descript = (TextView) findViewById(R.id.task_desc);
        descript.setText(desc.toString());

        TextView due = (TextView) findViewById(R.id.due);
        due.setText(due_date.toString());

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button finish_button = (Button) findViewById(R.id.finish);

        if(finish ==1){
            finish_button.setText("Unfinished");
        }
            finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("t_num", t_num);
                params.put("finish", finish);
                HttpClient.get("finishtask", params, new AsyncHttpResponseHandler() {
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




        ImageButton mab = (ImageButton) findViewById(R.id.mab);
        mab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //다이얼로그 띄워서 멤버리스트 보여준다음에 추가시키기 힣
                LayoutInflater layoutInflater = LayoutInflater.from(TaskInfo.this);
                View promptView = layoutInflater.inflate(R.layout.add_member_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TaskInfo.this);
                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.addMember);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                RequestParams params = new RequestParams();
                                params.put("email", input.getText().toString().trim());
                                params.put("t_num", t_num);
                                HttpClient.get("addmemberinproject", params, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                                    }

                                    @Override
                                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener(){

                                    public void onClick(DialogInterface dialog, int id){
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertD = alertDialogBuilder.create();
                alertD.setCanceledOnTouchOutside(true);
                alertD.show();
            }

        });


    }


}

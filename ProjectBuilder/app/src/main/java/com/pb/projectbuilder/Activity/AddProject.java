package com.pb.projectbuilder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pb.projectbuilder.R;

/**
 * Created by sanghee on 2015-11-07.
 */
public class AddProject extends AppCompatActivity {
    Intent intent;
    Button btnSave;
    EditText editText1;
    EditText editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project);

        intent=getIntent();
        btnSave=(Button) findViewById(R.id.add_todo_button);
        editText1=(EditText) findViewById(R.id.add_pj_name);
        editText2 = (EditText) findViewById(R.id.add_pj_date);

        /* 저장 버튼 */
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editText1.getText().toString().equals("")) {
                    Toast.makeText(AddProject.this, "please input!!", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra("pjname", editText1.getText().toString());
                    intent.putExtra("pjdate", editText2.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}
package com.pb.projectbuilder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.pb.projectbuilder.Adapter.ProjectAdapter;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Project;

import java.util.ArrayList;


public class ProjectList extends AppCompatActivity {
    ArrayList<Project> datas;
    //ArrayAdapter<Project> adapter;
    ProjectAdapter adapter;

    //ListView 참조변수
    ListView listView;
    Intent intent;
    FloatingActionButton btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list);

        datas = new ArrayList<Project>();
        datas.add(new Project("project1", "임종찬"));
        datas.add(new Project("project2", "윤상희"));

        // adapter = new ArrayAdapter<Project>(PjListActivity.this,android.R.layout.simple_list_item_1,datas);
        adapter = new ProjectAdapter(getLayoutInflater(), datas);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.addBtn);

          /* 추가 버튼 */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent=new Intent(ProjectList.this, AddProject.class);
                 startActivityForResult(intent, 0);

            }
        });



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        datas.add(new Project(data.getStringExtra("pjname").toString(),data.getStringExtra("pjdate").toString()));
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }



}
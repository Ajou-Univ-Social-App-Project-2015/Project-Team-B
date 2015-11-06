package com.pb.projectbuilder.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.pb.projectbuilder.Adapter.ProjectAdapter;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Project;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity {

    ArrayList<Project> datas= new ArrayList<Project>();

    //ListView 참조변수
    ListView listview;
    private Button AddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list);

        datas.add(new Project("project1", "임종찬"));
        datas.add( new Project("project2", "윤상희"));

        //ListView 객체 찾아와서 참조
        listview= (ListView)findViewById(R.id.listview);
        //button click 할 때 list추가
        AddButton =  (Button)  findViewById(R.id.addBtn);


        //첫번재 파라미터로 xml 레이아웃 파일을 객체로 만들어 주는 LayoutInflater 객체 얻어와서 전달..
        //두번째 파라미터는 우리가 나열한 Data 배열..
        final ProjectAdapter adapter= new ProjectAdapter( getLayoutInflater() , datas);

        listview.setAdapter(adapter);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.add(new Project("test1", "context"));
                adapter.notifyDataSetChanged();
            }
        });


        //위에 만든 Adapter 객체를 AdapterView의 일종인 ListView에 설정.
        listview.setAdapter(adapter);
    }
}


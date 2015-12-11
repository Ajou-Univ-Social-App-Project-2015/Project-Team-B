package com.pb.projectbuilder.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.Adapter.*;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Project;


import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {


    JSONArray arr;
    ProjectAdapter adapter;
    ListView listView;
    int p_num = 0;
    String p_name="";
    String m_name="";

    JSONArray taskArr;
    ListView myTaskList;
    TaskAdapter myTaskAdapter;


    public void init() {
        HttpClient.get("projectlist", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                adapter.setJsonArray(response);
                adapter.notifyDataSetChanged();
            }
        });
        HttpClient.get("mytask", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                myTaskAdapter.setJsonArray(response);
                myTaskAdapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

       init();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.l_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        m_name = intent.getExtras().getString("m_name");
        getSupportActionBar().setTitle(m_name);
        myTaskList = (ListView) findViewById(R.id.my_task_list);
        myTaskAdapter = new TaskAdapter(this,  taskArr);
        //리스트뷰 생성


        listView = (ListView) findViewById(R.id.listview);
        adapter = new ProjectAdapter(ProjectList.this, arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //해당 프로젝트의 number을 서버로 보내 세션에 저장시키고 그 프로젝트 페이지로 이동

                                                RequestParams params = new RequestParams();
                                                try {
                                                    p_num = adapter.getjsonArray().getJSONObject(position).getInt("p_num");
                                                    p_name = adapter.getjsonArray().getJSONObject(position).getString("p_name");
                                                    params.put("p_num", p_num);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }




                                                HttpClient.get("selectproject", params, new AsyncHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                                                    }

                                                    @Override
                                                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                                    }
                                                });

                                                Intent intent = new Intent(ProjectList.this, ProjectMain.class);
                                                intent.putExtra("p_num", p_num);
                                                intent.putExtra("p_name",p_name);
                                                intent.putExtra("m_name", m_name);
                                                startActivity(intent);

                                            }
                                        });




    //플로팅 버튼 세팅
    ImageButton fab = (ImageButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){

        Intent intent = new Intent(ProjectList.this, AddProject.class);
        startActivity(intent);

    }

    }

    );
    //네비게이션 레이아웃 세팅
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);





}


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //해야할 내업무 보여주기
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}

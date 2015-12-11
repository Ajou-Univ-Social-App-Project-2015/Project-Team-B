package com.pb.projectbuilder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Activity.AddTask;
import com.pb.projectbuilder.Activity.TaskInfo;
import com.pb.projectbuilder.Adapter.TaskAdapter;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pb.projectbuilder.R.layout.fragment_task;



public class TaskFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private ListView workingList;
    private ListView workedList;

    private TaskAdapter workingAdapter;
    private TaskAdapter workedAdapter;

    private JSONArray workingArr;
    private JSONArray workedArr;

    private String descript;
    private String due_date;
    private int t_num = 0;
    private String t_name = "";
    private int finish;
    private int mPage;

    public static TaskFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void init(){
        HttpClient.get("workinglist", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                workingAdapter.setJsonArray(response);
                workingAdapter.notifyDataSetChanged();

            }
        });

        HttpClient.get("workedlist", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                workedAdapter.setJsonArray(response);
                workedAdapter.notifyDataSetChanged();
            }
        });


    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_task, container, false);

        init();

        //작업중 리스트 생성
        final ToggleButton working = (ToggleButton)view.findViewById(R.id.working);
        working.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                init();
                return false;
            }
        });
        workingList = (ListView) view.findViewById(R.id.workinglist);
        workingAdapter = new TaskAdapter(getActivity(), workingArr);
        workingList.setAdapter(workingAdapter);
        workingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    t_num = workingAdapter.getJsonArray().getJSONObject(position).getInt("t_num");
                    t_name = workingAdapter.getJsonArray().getJSONObject(position).getString("t_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestParams params = new RequestParams();
                params.put("t_num", t_num);

                HttpClient.get("selecttask", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            descript = response.getString("descript");
                            due_date = response.getString("due_date");
                            finish = response.getInt("finish");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getContext(), TaskInfo.class);
                        intent.putExtra("t_num", t_num);
                        intent.putExtra("t_name", t_name);
                        intent.putExtra("descript", descript);
                        intent.putExtra("due_date", due_date);
                        intent.putExtra("finish", finish);
                        startActivity(intent);

                    }
                });


            }
        });


        //완료 리스트인데 버튼을 눌러야 리스트를 가져와서 세팅함
        workedList = (ListView) view.findViewById(R.id.workedlist);
        workedAdapter = new TaskAdapter(getActivity(), workedArr);
        workedList.setAdapter(workedAdapter);
        workedList.setVisibility(View.INVISIBLE);
        final ToggleButton worked = (ToggleButton)view.findViewById(R.id.worked);
        worked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (worked.isChecked()) {
                    workedList.setVisibility(View.VISIBLE);
                } else {
                    workedList.setVisibility(View.INVISIBLE);
                }
            }

        });
        //플로팅 버튼 세팅
        ImageButton fab = (ImageButton) view.findViewById(R.id.tfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTask.class);
                startActivity(intent);
            }

        });

        return view;
    }
}
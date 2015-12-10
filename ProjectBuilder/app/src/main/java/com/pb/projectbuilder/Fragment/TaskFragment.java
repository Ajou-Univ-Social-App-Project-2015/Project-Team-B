package com.pb.projectbuilder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.pb.projectbuilder.Activity.AddTask;
import com.pb.projectbuilder.Activity.ProjectList;
import com.pb.projectbuilder.Activity.ProjectMain;
import com.pb.projectbuilder.Adapter.TaskAdapter;
import com.pb.projectbuilder.Adapter.TestAdapter;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Task;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

import static com.pb.projectbuilder.R.layout.fragment_task;



public class TaskFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private ListView workingList;
    private ListView workedList;

    private TaskAdapter workingAdapter;
    private TaskAdapter workedAdapter;

//    private TestAdapter workingAdapter;
//    private TestAdapter workedAdapter;


    private JSONArray workingArr;
    private JSONArray workedArr;


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

    public static TaskFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_task, container, false);

        //플로팅 버튼 세팅
        ImageButton fab = (ImageButton) view.findViewById(R.id.tfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTask.class);
                startActivity(intent);
            }

        });

        ArrayList<String> test = new ArrayList<>();
        test.add("test1");
        //작업중 리스트 생성
        workingList = (ListView) view.findViewById(R.id.workinglist);
        workingAdapter = new TaskAdapter(getActivity(), workingArr);
        workingList.setAdapter(workingAdapter);
        final ToggleButton working = (ToggleButton)view.findViewById(R.id.working);
        working.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (working.isChecked()) {
                    workingList.setVisibility(View.INVISIBLE);

                } else {
                    workingList.setVisibility(View.VISIBLE);
                }
            }

        });




        //완료 리스트 생성
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

        return view;
    }
}
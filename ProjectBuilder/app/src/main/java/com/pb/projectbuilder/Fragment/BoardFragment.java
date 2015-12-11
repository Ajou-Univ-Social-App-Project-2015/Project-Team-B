package com.pb.projectbuilder.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Adapter.CommentAdapter;
import com.pb.projectbuilder.Adapter.RecyclerAdapter;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Comment;
import com.pb.projectbuilder.model.RecyclerCard;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;


public class BoardFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";


    Button btnAddCard;
    RecyclerView recyclerView;
    ArrayList<RecyclerCard> items = new ArrayList<RecyclerCard>();
    RecyclerAdapter recyclerAdapter;
    EditText etDescription;
    String description = "";
    ////////////////////////////////////////////
    ArrayList<Comment> commentdatas;
    CommentAdapter commentadapter;
    ListView dialog_ListView;
    Button btnAddComment;
    EditText editComment;
    String descrip = "";
    View dialogView;
   android.os.Handler dialogHandler;
    JSONArray jsonArray;
    int b_num;
    private int mPage;

    public static BoardFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BoardFragment fragment = new BoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void requestBoardlist() {
        RequestParams params = new RequestParams();

        HttpClient.get("boardlist", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    ArrayList<RecyclerCard> items = new ArrayList<RecyclerCard>();
                    recyclerAdapter.setItems(items);
                    recyclerAdapter.setJsonArray(response);
                    recyclerAdapter.notifyDataSetChanged();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void requestCommentlist(int b_num) {
        RequestParams params = new RequestParams();
        params.put("b_num", b_num);

        HttpClient.get("commentlist", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                commentadapter.setJsonArray(response);
                commentadapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }


    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        commentdatas = new ArrayList<Comment>();
        commentadapter = new CommentAdapter(getActivity(), jsonArray);
        requestBoardlist();
        dialogHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:

                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                        dialogView = inflater.inflate(R.layout.comment_dialog, null);
                        b_num = msg.getData().getInt("b_num");
                        //Dialog 생성 및 보이기
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//AlertDialog.Builder 객체 생성
                        builder.setTitle("comments"); //Dialog 제목
                        builder.setView(dialogView);//위의 inflater가 만든 dialogView 객체 셋팅 (Customize)

                        //prepare ListView in dialog

                        requestCommentlist(b_num);
                        dialog_ListView = (ListView) dialogView.findViewById(R.id.listview);
                        dialog_ListView.setAdapter(commentadapter);
                        btnAddComment = (Button) dialogView.findViewById(R.id.comment_btn);
                        editComment = (EditText) dialogView.findViewById(R.id.comment_input);
                        btnAddComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                descrip = editComment.getText().toString();
                                RequestParams params = new RequestParams();
                                params.put("content", descrip);
                                params.put("b_num", b_num);
                                HttpClient.get("addcomment", params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                        super.onSuccess(statusCode, headers, response);
                                    }
                                });
                                Comment mLog = new Comment(descrip, "time");
                                commentdatas.add(mLog);

                                commentadapter.notifyDataSetChanged();
                                editComment.setText("");

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(true);//Dialog 바깥 터치시 Dialog 없애기

                        dialog.show();//dialog 보이기
                        break;
                }
            }
        };


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerAdapter = new RecyclerAdapter(dialogHandler, getContext(), items);
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        etDescription = (EditText) view.findViewById(R.id.descrip);
        btnAddCard = (Button) view.findViewById(R.id.button);
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = etDescription.getText().toString();
                RequestParams params = new RequestParams();
                params.put("content", description);

                HttpClient.get("addboard", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                    }
                });
                Intent intent = getActivity().getIntent();
                    String m_name = intent.getExtras().getString("m_name");
                    RecyclerCard mLog = new RecyclerCard(m_name, description, 0);
                   // items.add(mLog);
                etDescription.setText("");
                recyclerAdapter.setData(mLog);
                recyclerAdapter.notifyDataSetChanged();


            }
        });

        return view;
    }
}
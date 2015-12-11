package com.pb.projectbuilder.Adapter;

/**
 * Created by jongchan on 15. 12. 11..
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.RecyclerCard;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by sanghee on 2015-11-25.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    Context context;
    ArrayList<RecyclerCard> items = new ArrayList<RecyclerCard>();
    android.os.Handler mHandler;
    Intent intent;
    int b_num;
    private JSONArray jsonArray;

    public RecyclerAdapter( android.os.Handler mHandler, Context context, ArrayList<RecyclerCard> items ){
        this.mHandler = mHandler;
        this.context=context;
        this.items=items;
    }

    public void setItems(ArrayList<RecyclerCard> items) {
        this.items = items;
    }

    public void setData(RecyclerCard item){
        items.add(item);
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final RecyclerCard item = items.get(i);
        viewHolder.content.setText(item.getTitle());
        viewHolder.member_name.setText(item.getm_name());
        viewHolder.btnCardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = mHandler.obtainMessage(1);
                    Bundle bundle = new Bundle();
                    bundle.putString("",null);
                    bundle.putInt("n", 1);
                    bundle.putInt("b_num", item.getB_num());
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public int getItemCount(){
        return this.items.size();
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        JSONObject obj = new JSONObject();
        for(int i = 0; i< jsonArray.length(); i++){
            try {
                obj =  jsonArray.optJSONObject(i);
                items.add(new RecyclerCard(obj.getString("m_name"), obj.getString("content"), obj.getInt("b_num")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView member_name;
        CardView cardView;
        Button btnCardButton;



        public ViewHolder(View itemView){
            super(itemView);
            content=(TextView)itemView.findViewById(R.id.content);
            member_name=(TextView)itemView.findViewById(R.id.member_name);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            btnCardButton=(Button)itemView.findViewById(R.id.comment_button);
            intent =  ((Activity) context).getIntent();
            //cardview onclick listener
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(context,"card click",Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder adbb = new AlertDialog.Builder(context);
                    // adbb.setTitle("Hi");
                    adbb.setMessage("Are you want to add notice?");
                    adbb.setCancelable(false);
                    String yesButtonText = "Yes";
                    String noButtonText = "No";

                    adbb.setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //여기에 Main notice list에다 추가하는 코드 작성 하기!!!!
                            //title내용을 notice해야 함
                            //intent.putExtra("content",title.getText().toString());
                            // ((Activity) context).setResult(((Activity) context).RESULT_OK, intent);
                            //((Activity) context).finish();
                            RequestParams params = new RequestParams();
                            params.put("content",content.getText());
                            HttpClient.get("addnotice", params, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                }
                            });
                        }
                    });

                    adbb.setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    adbb.show();

                    return true;
                }
            });


        }
    }



}
package com.pb.projectbuilder.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sanghee on 2015-11-25.
 */
public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private JSONArray jsonArray;
    private ToggleButton finish;

    public CommentAdapter (Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
    }



    public ToggleButton getFinish() {
        return finish;
    }

    public void setFinish(ToggleButton finish) {
        this.finish = finish;
    }

    public void setJsonArray(JSONArray arr){
        this.jsonArray = arr;
    }

    public JSONArray getJsonArray () {
        return jsonArray;
    }

    @Override public int getCount() {
        if(null==jsonArray)
            return 0;
        else
            return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
        if(null==jsonArray) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("t_name");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.comment_row, null);


        TextView text_email= (TextView)convertView.findViewById(R.id.member_name);
        TextView text_content= (TextView)convertView.findViewById(R.id.content);
        TextView text_date= (TextView)convertView.findViewById(R.id.time);
        JSONObject json_data = getItem(position);
        if(null!=json_data ){
            //태스크 이름 설정
            String email ="";
            String date= "";
            String content ="";
            try {
                email = json_data.getString("email");
                date = json_data.getString("date");
                content = json_data.getString("content");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            text_email.setText(email.toString());
            text_content.setText(content.toString());
            text_date.setText(date.toString());
            //태스크 완료 버튼 설정

        }


        // ImageView img_flag= (ImageView)convertView.findViewById(R.id.img_flag);

        //현재 position( getView()메소드의 첫번재 파라미터 )번째의 Data를 위 해당 View들에 연결..


        //설정이 끝난 convertView객체 리턴(ListView에서 목록 하나.)
        return convertView;
    }
}
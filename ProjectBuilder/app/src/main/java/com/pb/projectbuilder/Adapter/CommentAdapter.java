package com.pb.projectbuilder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Comment;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by sanghee on 2015-11-25.
 */
public class CommentAdapter extends BaseAdapter {
    ArrayList<Comment> datas;
    LayoutInflater inflater;
    JSONArray jsonArray;

    public CommentAdapter(LayoutInflater inflater, ArrayList<com.pb.projectbuilder.model.Comment> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_row, null);
        }
        TextView text_name= (TextView)convertView.findViewById(R.id.content);
        TextView text_date= (TextView)convertView.findViewById(R.id.time);
        // ImageView img_flag= (ImageView)convertView.findViewById(R.id.img_flag);

        //현재 position( getView()메소드의 첫번재 파라미터 )번째의 Data를 위 해당 View들에 연결..
        text_name.setText( datas.get(position).getComment());
        text_date.setText( datas.get(position).getDate() );

        //설정이 끝난 convertView객체 리턴(ListView에서 목록 하나.)
        return convertView;
    }
}
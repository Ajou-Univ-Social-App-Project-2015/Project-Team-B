package com.pb.projectbuilder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pb.projectbuilder.Activity.ProjectMain;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jongchan on 15. 11. 18..
 */
public class TaskAdapter  extends BaseAdapter implements ListAdapter {

    private Activity activity;
    private JSONArray jsonArray;
    private  ToggleButton finish;

    public TaskAdapter (Activity activity, JSONArray jsonArray) {
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

    @Override public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.list_row, null);

        TextView text =(TextView)convertView.findViewById(R.id.p_name);

        JSONObject json_data = getItem(position);
        if(null!=json_data ){
            //태스크 이름 설정
            String jj= null;
            try {
                jj = json_data.getString("t_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            text.setText(jj);

            //태스크 완료 버튼 설정

        }

        return convertView;
    }

}

package com.pb.projectbuilder.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.pb.projectbuilder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jongchan on 15. 11. 14..
 */
public class ProjectAdapter extends BaseAdapter implements ListAdapter {

    private  Activity activity;
    private  JSONArray jsonArray;

    public ProjectAdapter(Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
    }

    public void setJsonArray(JSONArray arr){
        this.jsonArray = arr;
    }

    public JSONArray getjsonArray(){
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

        return jsonObject.optLong("p_name");
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.list_row, null);

        TextView text =(TextView)convertView.findViewById(R.id.p_name);

        JSONObject json_data = getItem(position);
        if(null!=json_data ){
            String jj= null;
            try {
                jj = json_data.getString("p_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            text.setText(jj);
        }

        return convertView;
    }
}

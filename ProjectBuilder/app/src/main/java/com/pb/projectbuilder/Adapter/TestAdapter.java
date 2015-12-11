package com.pb.projectbuilder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.model.Project;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by jongchan on 15. 11. 18..
 */
public class TestAdapter extends BaseAdapter {
    ArrayList<String> datas;
    LayoutInflater inflater;

    public TestAdapter(LayoutInflater inflater, ArrayList<String> datas) {
        this.datas = datas;
        this.inflater = inflater;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.task_row, null);

        TextView text = (TextView) convertView.findViewById(R.id.t_name);
        text.setText(datas.get(position));

        final ToggleButton toggleButton = (ToggleButton) convertView.findViewById(R.id.finish_button);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    RequestParams params = new RequestParams();
                    params.put("email",datas.get(position).toString().trim());
                    HttpClient.get("finishTask", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            datas.remove(position);
                            datas.notify();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });
                } else {
                    toggleButton.setText("");
                }
            }
        });
        return convertView;
    }
}

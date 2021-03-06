package com.pb.projectbuilder.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pb.projectbuilder.Activity.ProjectList;
import com.pb.projectbuilder.Adapter.NoticeAdapter;
import com.pb.projectbuilder.Adapter.ProjectAdapter;
import com.pb.projectbuilder.Adapter.TestAdapter;
import com.pb.projectbuilder.Connecter.HttpClient;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.View.CalendarView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class MainFragment extends Fragment implements CalendarView.RobotoCalendarListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    private CalendarView calendarView;
    private int currentMonthIndex ;
    private Calendar currentCalendar;

    NoticeAdapter adapter;
    ListView listView;
    JSONArray arr;
    int n_num;


    ArrayList<String> test ;

    Intent intent;

    public void init() {
        HttpClient.get("noticelist", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                adapter.setJsonArray(response);
                adapter.notifyDataSetChanged();
            }
        });


    }

    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init();
        calendarView = (CalendarView)view.findViewById(R.id.robotoCalendarPicker);
        // calendarView = new CalendarView(getContext());
        calendarView.setRobotoCalendarListener(this);

        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        // Mark current day
        calendarView.markDayAsCurrentDay(currentCalendar.getTime());



        listView = (ListView) view.findViewById(R.id.noticelist);
        adapter = new NoticeAdapter(getActivity(), arr);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                try {
                    n_num = adapter.getjsonArray().getJSONObject(position).getInt("n_num");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder adbb = new AlertDialog.Builder(getContext());
                // adbb.setTitle("Hi");
                adbb.setMessage("Are you want to delete notice?");
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
                        params.put("n_num", n_num);
                        HttpClient.get("deletenotice", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });

                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
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




        return view;
    }


    @Override
    public void onDateSelected(Date date) {
        // Mark calendar day
        calendarView.markDayAsSelectedDay(date);

        // Mark that day with random colors
        final Random random = new Random(System.currentTimeMillis());
        final int style = random.nextInt(3);
        switch (style) {
            case 0:
                calendarView.markFirstUnderlineWithStyle(CalendarView.BLUE_COLOR, date);
                break;
            case 1:
                calendarView.markSecondUnderlineWithStyle(CalendarView.GREEN_COLOR, date);
                break;
            case 2:
                calendarView.markFirstUnderlineWithStyle(CalendarView.RED_COLOR, date);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        calendarView.initializeCalendar(currentCalendar);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        test.add(data.getStringExtra("content").toString());
        adapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }



}
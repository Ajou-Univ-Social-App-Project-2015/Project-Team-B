package com.pb.projectbuilder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pb.projectbuilder.Adapter.TestAdapter;
import com.pb.projectbuilder.R;
import com.pb.projectbuilder.View.CalendarView;

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

    private ListView noticeList;
    private TestAdapter workingAdapter;

    ArrayList<String> test ;

    Intent intent;

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

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        calendarView = (CalendarView)view.findViewById(R.id.robotoCalendarPicker);
        // calendarView = new CalendarView(getContext());
        calendarView.setRobotoCalendarListener(this);

        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        // Mark current day
        calendarView.markDayAsCurrentDay(currentCalendar.getTime());

        test = new ArrayList<String>();
        test.add("test1");
        test.add("test2");
        test.add("test3");
        workingAdapter = new TestAdapter(inflater, test);;

        noticeList = (ListView) view.findViewById(R.id.noticelist);
        noticeList.setAdapter(workingAdapter);



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
        workingAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }



}
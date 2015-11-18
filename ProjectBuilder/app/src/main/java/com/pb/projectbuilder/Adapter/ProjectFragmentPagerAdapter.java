package com.pb.projectbuilder.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pb.projectbuilder.Fragment.BoardFragment;
import com.pb.projectbuilder.Fragment.MainFragment;
import com.pb.projectbuilder.Fragment.TaskFragment;

/**
 * Created by jongchan on 15. 11. 16..
 */
public class ProjectFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };

    public ProjectFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return MainFragment.newInstance(position + 1);
            case 1: return TaskFragment.newInstance(position + 1);
            case 2: return BoardFragment.newInstance(position + 1);
        }
        return MainFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
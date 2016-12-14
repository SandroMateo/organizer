package com.example.guest.organizer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.organizer.ui.TaskDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/13/16.
 */
public class DatePagerAdapter extends FragmentPagerAdapter{
    private List<String> mDates;

    public DatePagerAdapter(FragmentManager fm, List<String> dates) {
        super(fm);
        mDates = dates;
    }

    @Override
    public int getCount() {
        return mDates.size();
    }

    @Override
    public Fragment getItem(int position) {
        return TaskDetailFragment.newInstance(mDates.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDates.get(position);
    }
}

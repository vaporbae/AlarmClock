package com.example.vapor.alarmproject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new WorldClocksFragment();
        } else if (position == 1){
            return new AlarmClockFragment();
        } else if (position == 2){
            return new TimeToSleepFragment();
        } else {
            return new WakeMeUpHereFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.World_clocks);
            case 1:
                return mContext.getString(R.string.Alarm_clocks);
            case 2:
                return mContext.getString(R.string.Time_to_sleep);
            case 3:
                return mContext.getString(R.string.Wake_me_up_here);
            default:
                return null;
        }
    }



}

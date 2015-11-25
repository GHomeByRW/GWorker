package com.ghomebyrw.gworker.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ghomebyrw.gworker.fragments.AccountFragment;
import com.ghomebyrw.gworker.fragments.JobsFragment;
import com.ghomebyrw.gworker.fragments.NotificationsFragment;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    public static final int ACCOUNT_TAB = 0;
    public static final int JOBS_TAB = 1;
    public static final int NOTIFICATIONS_TAB = 2;

    final int PAGE_COUNT = 3;

    //TODO: replace string titles with icons
    private String tabTitles[] = new String[] { "Account", "Jobs", "Notifications" };

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == ACCOUNT_TAB) {
            return new AccountFragment();
        } else if (position == JOBS_TAB) {
            return new JobsFragment();
        } else {
            return new NotificationsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

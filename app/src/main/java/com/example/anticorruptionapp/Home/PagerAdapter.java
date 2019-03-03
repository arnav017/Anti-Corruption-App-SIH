package com.example.anticorruptionapp.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.anticorruptionapp.Home.Fragment.CurrentPolicyFragment;
import com.example.anticorruptionapp.Home.Fragment.IssuesPolicyFragment;
import com.example.anticorruptionapp.Home.Fragment.PastPolicyFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int no_of_tabs;

    public PagerAdapter(FragmentManager fm, int no_of_tabs) {
        super(fm);
        this.no_of_tabs = no_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return (new CurrentPolicyFragment());
            case 1: return (new PastPolicyFragment());
            case 2: return (new IssuesPolicyFragment());
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}

package com.example.uidesign.Tab_Layout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Tab_Adapter extends FragmentPagerAdapter {

    private final Context context;
    int totalTabs;

    public Tab_Adapter( FragmentManager fm,  Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                FirstFragment firstFragment = new FirstFragment();
                return firstFragment;
            case 1:
                SecondFragment secondFragment = new SecondFragment();
                return secondFragment;
            case 2:
                ThirdFragment thirdFragment = new ThirdFragment();
                return thirdFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

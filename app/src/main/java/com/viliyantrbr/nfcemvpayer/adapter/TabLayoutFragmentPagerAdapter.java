package com.viliyantrbr.nfcemvpayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabLayoutFragmentPagerAdapter extends FragmentPagerAdapter {
    // Adapter interface
    public interface ITabLayoutFragmentPagerAdapter {
        Fragment getItem();
        CharSequence getPageTitle();

        int getIcon();
    }
    // - Adapter interface

    private ArrayList<ITabLayoutFragmentPagerAdapter> mArrayList = null;

    public TabLayoutFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<ITabLayoutFragmentPagerAdapter> arrayList) {
        super(fragmentManager);

        mArrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        if (mArrayList != null) {
            return mArrayList.get(position).getItem();
        }

        return null;
    }

    @Override
    public int getCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }

        return -1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mArrayList != null) {
            return mArrayList.get(position).getPageTitle();
        }

        return null;
    }
}

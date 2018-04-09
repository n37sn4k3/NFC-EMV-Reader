package com.viliyantrbr.nfcemvpayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabLayoutFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    // Adapter interface
    public interface ITabLayoutFragmentStatePagerAdapter {
        Fragment getItem();
        CharSequence getPageTitle();

        /*int getIcon();*/
    }
    // - Adapter interface

    private ArrayList<ITabLayoutFragmentStatePagerAdapter> mArrayList = null;

    public TabLayoutFragmentStatePagerAdapter(FragmentManager fragmentManager, ArrayList<ITabLayoutFragmentStatePagerAdapter> arrayList) {
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

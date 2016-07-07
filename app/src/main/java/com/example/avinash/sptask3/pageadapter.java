package com.example.avinash.sptask3;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by AVINASH on 7/6/2016.
 */
public class pageadapter extends FragmentStatePagerAdapter {
    int tablen;

    public pageadapter(FragmentManager fm,int noT){
        super(fm);
        this.tablen=noT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: tab1 t=new tab1();
                return t;
            case 1: tab2 t2=new tab2();
                return t2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tablen;
    }
}

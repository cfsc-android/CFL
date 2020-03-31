package com.chanfinecloud.cfl.adapter.smart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Loong on 2020/2/12.
 * Version: 1.0
 * Describe: 首页待处理ViewPaper适配器
 */
public class HouseholdPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    public HouseholdPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override//返回要显示的碎片
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override//返回要显示多少页
    public int getCount() {
        return list.size();
    }

}

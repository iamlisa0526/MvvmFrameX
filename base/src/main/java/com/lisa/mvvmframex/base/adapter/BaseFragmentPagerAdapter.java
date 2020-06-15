package com.lisa.mvvmframex.base.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Description: BaseFragmentPagerAdapter
 * @Author: lisa
 * @CreateDate: 2020/6/8 09:10
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

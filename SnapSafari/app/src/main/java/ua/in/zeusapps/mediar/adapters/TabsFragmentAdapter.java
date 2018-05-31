package ua.in.zeusapps.mediar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ua.in.zeusapps.mediar.fragments.login.LogInFragment;
import ua.in.zeusapps.mediar.fragments.login.RegisterFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter {
    private List<String> mList;

    public TabsFragmentAdapter(FragmentManager fragmentManager, List<String> list) {
        super(fragmentManager);
        mList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LogInFragment.getInstance();
            case 1:
                return RegisterFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
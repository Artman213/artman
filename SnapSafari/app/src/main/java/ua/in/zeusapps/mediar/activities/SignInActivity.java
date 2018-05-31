package ua.in.zeusapps.mediar.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.adapters.TabsFragmentAdapter;
import ua.in.zeusapps.mediar.common.Layout;
import ua.in.zeusapps.mediar.fragments.login.LogInFragment;

/**
 * Created by slava on 04.12.17.
 */
@Layout(R.layout.activity_sign_in)
public class SignInActivity extends ActivityBase implements LogInFragment.OnSaveToken {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initTabs();
    }

    private void initTabs() {
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        List<String> mList = new ArrayList<>();
        mList.add(getString(R.string.tab_log_in));
        mList.add(getString(R.string.tab_register));

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        final boolean isKeyboardShow = false;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onSaveToken(String token) {
        saveToken(token);
    }
}

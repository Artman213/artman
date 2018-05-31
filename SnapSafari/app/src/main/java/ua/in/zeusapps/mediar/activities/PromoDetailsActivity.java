package ua.in.zeusapps.mediar.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.common.Layout;
import ua.in.zeusapps.mediar.controls.LockableViewPager;
import ua.in.zeusapps.mediar.controls.MenuDark;
import ua.in.zeusapps.mediar.controls.PageIndicator;
import ua.in.zeusapps.mediar.fragments.PromoDescriptionFragment;
import ua.in.zeusapps.mediar.fragments.PromoDetailsFragment;
import ua.in.zeusapps.mediar.models.Card;

@Layout(R.layout.activity_promo_details)
public class PromoDetailsActivity extends ActivityBase {
    public static final String CARD_EXTRA = "card";

    private Card _card;
    private PromoDetailsFragment _promoDetailsFragment;
    private PromoDescriptionFragment _promoDescriptionFragment;
    private PageIndicator _pageIndicator;
    private CustomPagerAdapter mAdapter;

    @BindView(R.id.pagesContainer)
    LinearLayout _linerLayout;
    @BindView(R.id.activity_promo_details_view_pager)
    LockableViewPager _viewPager;

    @BindView(R.id.activity_promo_details_menu)
    MenuDark menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _card = getIntent().getParcelableExtra(CARD_EXTRA);
        String type = "LC Waikiki " + _card.getTitle();
        String days = "Valid for " + getDaysBetween(_card) + " days";

        _promoDetailsFragment = PromoDetailsFragment
                .newInstance(type
                        , _card.getPromo().getTerm()
                        , R.drawable.qr_code);

        _promoDescriptionFragment = PromoDescriptionFragment
                .newInstance(type
                        , _card.getPromo().getDescription()
                        , days);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(_promoDescriptionFragment);
        fragments.add(_promoDetailsFragment);

        mAdapter = new PromoDetailsActivity.CustomPagerAdapter(getSupportFragmentManager(), fragments);
        _viewPager.setAdapter(mAdapter);
        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    _viewPager.setSwipeable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        _pageIndicator = new PageIndicator(this, _linerLayout, _viewPager, R.drawable.fragment_indicator);
        _pageIndicator.setPageCount(fragments.size());
        _pageIndicator.show();
    }

    private int getElementResource(Card card) {
        switch (card.getElement()) {
            case "W":
                return R.drawable.ic_water;
            case "A":
                return R.drawable.ic_air;
            case "F":
                return R.drawable.ic_fire;
            case "E":
                return R.drawable.ic_earth;
        }

        return 0;
    }

    static class CustomPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> mFrags = new ArrayList<>();

        public CustomPagerAdapter(FragmentManager fm, List<Fragment> frags) {
            super(fm);
            mFrags = frags;
        }

        @Override
        public Fragment getItem(int position) {
            int index = position % mFrags.size();
            return mFrags.get(index);
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

    private int getDaysBetween(Card card) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());

        Date expire = new Date();
        Date today = new Date();
        try {
            expire = dateFormat.parse(card.getPromo().getExpiryDate());
            long diff = expire.getTime() - today.getTime();
            if (diff < 0) {
                return 0;
            }

            return (int) (diff / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

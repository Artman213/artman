package ua.in.zeusapps.mediar.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.common.Layout;
import ua.in.zeusapps.mediar.fragments.BoostCardsFragment;
import ua.in.zeusapps.mediar.fragments.ElephantFragment;
import ua.in.zeusapps.mediar.fragments.FragmentBase;
import ua.in.zeusapps.mediar.fragments.PromoFragment;
import ua.in.zeusapps.mediar.models.Card;

@Layout(R.layout.activity_snap_cards)
public class SnapCardsActivity extends ActivityBase {

    private String _selectedFilter = "earth";

    private List<Card> _snappedCards;
    private ElephantFragment _elephantFragment;
    private BoostCardsFragment _boostCardsFragment;
    private PromoFragment _promoFragment;
    private TabViewHolder _tabHolder1;
    private TabViewHolder _tabHolder2;
    private TabViewHolder _tabHolder3;


    private List<FilterHolder> _filters = new ArrayList<>();


    @BindView(R.id.activity_snap_cards_tab_layout)
    TabLayout _tabLayout;
    @BindView(R.id.activity_snap_cards_view_pager)
    ViewPager _viewPager;
    @BindView(R.id.activity_snap_cards_filter_earth)
    Button _earthButton;
    @BindView(R.id.activity_snap_cards_filter_air)
    Button _airButton;
    @BindView(R.id.activity_snap_cards_filter_fire)
    Button _fireButton;
    @BindView(R.id.activity_snap_cards_filter_water)
    Button _waterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initTabs();
    }

    private void init(){
        _elephantFragment = new ElephantFragment();
        _boostCardsFragment = new BoostCardsFragment();
        _promoFragment = new PromoFragment();

        _viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        _tabLayout.setupWithViewPager(_viewPager);
        _tabLayout.setTabTextColors(Color.BLACK, Color.BLACK);

        _filters.add(new FilterHolder(_earthButton, "earth", R.drawable.earth_green, R.drawable.earth_white));
        _filters.add(new FilterHolder(_airButton, "air", R.drawable.air_green, R.drawable.air_white));
        _filters.add(new FilterHolder(_fireButton, "fire", R.drawable.fire_green, R.drawable.fire_white));
        _filters.add(new FilterHolder(_waterButton, "water", R.drawable.water_green, R.drawable.water_white));

        defaultBottomButtonsColorFilter("earth");

        getApp().getService().getMyCards(getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Card>>() {
                    @Override
                    public void accept(@NonNull List<Card> snappedCards) throws Exception {
                        _snappedCards = snappedCards;

                        update();
                    }
                });
    }

    void initTabs(){
        View tab = getNewTab();
        _tabHolder1 = new TabViewHolder(tab);
        _tabHolder1.setTitle(getString(R.string.activity_snap_cards_snapped_animals));
        _tabLayout.getTabAt(0).setCustomView(tab);

        tab = getNewTab();
        _tabHolder2 = new TabViewHolder(tab);
        _tabHolder2.setTitle(getString(R.string.activity_snap_cards_boost_cards));
        _tabLayout.getTabAt(1).setCustomView(tab);

        tab = getNewTab();
        _tabHolder3 = new TabViewHolder(tab);
        _tabHolder3.setTitle(getString(R.string.activity_snap_cards_promo_cards));
        _tabLayout.getTabAt(2).setCustomView(tab);
    }

    private void defaultBottomButtonsColorFilter(String without) {
        for (FilterHolder holder: _filters) {
            if (holder._element.equals(without)){
                continue;
            }
            holder.getButton().setBackgroundColor(Color.TRANSPARENT);
            Drawable bottom = ContextCompat.getDrawable(this, holder.getImage());
            bottom.setColorFilter(Color.argb(255, 9,57,71), PorterDuff.Mode.SRC_IN);
            holder.getButton().setCompoundDrawablesWithIntrinsicBounds(null, null, null, bottom);
        }
    }

    private void update(){
        int promoCardsCount = 0;
        List<Card> promoCards = new ArrayList<>();
        List<Card> cards = new ArrayList<>();


        for (Card card: _snappedCards){
            if (card.getPromo() != null){
                promoCardsCount++;
                if (card.getElement().equals(_selectedFilter)){
                    promoCards.add(card);
                }
            }

            if (card.getElement().equals(_selectedFilter)){
                cards.add(card);
            }
        }

        _elephantFragment.addCards(cards);
        _promoFragment.addCards(promoCards);

        _tabHolder1.setDescription(
                getString(R.string.activity_snap_cards_description,
                        cards.size(),
                        _snappedCards.size()));
        _tabHolder3.setDescription(
                getString(R.string.activity_snap_cards_description,
                        promoCards.size(),
                        promoCardsCount));
    }

    @OnClick({
            R.id.activity_snap_cards_filter_earth,
            R.id.activity_snap_cards_filter_air,
            R.id.activity_snap_cards_filter_fire,
            R.id.activity_snap_cards_filter_water,
    })
    public void onFilter(Button btn){
        int id = btn.getId();

        for (FilterHolder holder: _filters) {
            if (holder.getId() == id){
                holder.getButton()
                        .setBackgroundColor(
                                ContextCompat.getColor(this, R.color.colorPrimary));
                Drawable bottom = ContextCompat.getDrawable(this, holder.getSelectedImage());
                holder.getButton().setCompoundDrawablesWithIntrinsicBounds(null, null, null, bottom);
                _selectedFilter = holder.getElement();
            } else {
                holder.getButton().setBackgroundColor(Color.TRANSPARENT);
                Drawable bottom = ContextCompat.getDrawable(this, holder.getImage());
                bottom.setColorFilter(Color.argb(255, 9,57,71), PorterDuff.Mode.SRC_IN);
                holder.getButton().setCompoundDrawablesWithIntrinsicBounds(null, null, null, bottom);
            }
        }

        update();
    }

    private View getNewTab(){
        return getLayoutInflater().inflate(R.layout.tab, null, false);
    }

    class Adapter extends FragmentPagerAdapter{

        private List<FragmentBase> _fragments = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);

            _fragments.add(_elephantFragment);
            _fragments.add(_boostCardsFragment);
            _fragments.add(_promoFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return _fragments.get(position);
        }

        @Override
        public int getCount() {
            return _fragments.size();
        }
    }

    private class FilterHolder {
        private Button _button;
        private int _id;
        private String _element;
        private int _selectedImage;
        private int _image;

        public FilterHolder(Button button, String element, int image, int selectedImage) {
            _button = button;
            _id = button.getId();
            _element = element;
            _image = image;
            _selectedImage = selectedImage;
        }

        public Button getButton() {
            return _button;
        }

        public int getId() {
            return _id;
        }

        public String getElement() {
            return _element;
        }

        public int getSelectedImage() {
            return _selectedImage;
        }

        public int getImage() {
            return _image;
        }
    }

    public class TabViewHolder{

        @BindView(R.id.tab_title)
        TextView _title;
        @BindView(R.id.tab_description)
        TextView _description;

        public TabViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setTitle(String title){
            _title.setText(title);
        }

        public void setDescription(String description){
            _description.setText(description);
        }
    }
}

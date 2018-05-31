package ua.in.zeusapps.mediar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.common.Layout;
import ua.in.zeusapps.mediar.controls.MenuDark;
import ua.in.zeusapps.mediar.models.Card;
import ua.in.zeusapps.mediar.models.SnapRequest;

@Layout(R.layout.activity_promo)
public class PromoActivity extends ActivityBase {

    public static final String CARD_EXTRA = "card";

    private Card _card;

    @BindView(R.id.activity_promo_title)
    TextView title;
    @BindView(R.id.activity_promo_second_title)
    TextView secondTitle;
    @BindView(R.id.activity_promo_valid_for)
    TextView validFor;
    @BindView(R.id.activity_promo_term)
    TextView term;
    @BindView(R.id.activity_promo_menu)
    MenuDark menu;

    @BindView(R.id.activity_promo_elephant)
    ImageView elephantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _card = getIntent().getParcelableExtra(CARD_EXTRA);
        showCard(_card);
    }

    private void showCard(Card card) {
        title.setText(card.getTitle());
        secondTitle.setText(card.getSecondTitle());
//        description.setText(card.getPromo().getTitle());

        term.setText(card.getPromo().getTerm());

        Picasso.with(this).load(getApp().getUri(card.getImage())).into(elephantImage);
//        Picasso.with(this).load(getApp().getUri(card.getPromo().getImage())).into(promoImage);
    }

    @OnClick(R.id.activity_promo_snap_button)
    public void onSnap() {
        if (_card == null) {
            return;
        }

        SnapRequest request = new SnapRequest(_card.getId());
        getApp().getService().snapCard(getToken(), request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Card>() {
                    @Override
                    public void accept(@NonNull Card snappedCard) throws Exception {
                        Toast.makeText(PromoActivity.this, "Card snapped!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PromoActivity.this, SnapCardsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(PromoActivity.this, "Failed to snap card. Try later", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @OnClick(R.id.activity_promo_redeem_button)
//    public void onRedeem(){
//        Intent intent = new Intent(this, PromoDetailsActivity.class);
//        intent.putExtra(PromoDetailsActivity.CARD_EXTRA, _card);
//
//        startActivity(intent);
//    }

//    @OnClick(R.id.activity_promo_back)
//    public void onBack(){
//        finish();
//    }

    private int getIconRes(Card card) {
        switch (card.getElement()) {
            case "A":
                return R.drawable.ic_air;
            case "E":
                return R.drawable.ic_earth;
            case "W":
                return R.drawable.ic_water;
            case "F":
                return R.drawable.ic_fire;
        }

        return 0;
    }
}

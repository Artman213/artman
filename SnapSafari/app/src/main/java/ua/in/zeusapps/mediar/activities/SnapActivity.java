package ua.in.zeusapps.mediar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

@Layout(R.layout.activity_snap)
public class SnapActivity extends ActivityBase {

    public static final String CARD_EXTRA = "card";

    private int _cardId = -1;
    @BindView(R.id.activity_snap_image)
    ImageView _imageImageView;
    @BindView(R.id.activity_snap_icon)
    ImageView _iconImageView;
    @BindView(R.id.activity_snap_label)
    TextView _labelTextView;
    @BindView(R.id.activity_snap_level)
    TextView _levelTextView;
    @BindView(R.id.activity_snap_description)
    TextView _descriptionTextView;
    @BindView(R.id.activity_snap_redeem_button)
    Button _redeemButton;
    @BindView(R.id.activity_promo_menu)
    MenuDark menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Card card = getIntent().getParcelableExtra(CARD_EXTRA);
        showCard(card);
        setListener(card);
    }

    private void setListener(final Card card) {
        _redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnapActivity.this, PromoDetailsActivity.class);
                intent.putExtra(CARD_EXTRA, card);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.activity_snap_snap_it_button)
    public void onSnap(){
        if (_cardId == -1){
            return;
        }

        getApp()
                .getService()
                .snapCard(getToken(), new SnapRequest(_cardId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Card>() {
                    @Override
                    public void accept(@NonNull Card snappedCard) throws Exception {
                        String message = _labelTextView.getText().toString() + " snapped!";
                        Toast.makeText(SnapActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SnapActivity.this, SnapCardsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(SnapActivity.this, "Failed to snap. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.activity_snap_redeem_button)
    public void onRedeem(){
    }

    private void showCard(Card card){
        _cardId = card.getId();
        if (card.getPromo() == null) {
            _redeemButton.setVisibility(View.GONE);
        }

        String imageName = card.getImageName();
        int res = getResources().getIdentifier(imageName, "drawable", getPackageName());

        Picasso
                .with(this)
                .load(res)
                .into(_imageImageView);
        Picasso
                .with(this)
                .load(getElementResource(card))
                .into(_iconImageView);

        _labelTextView.setText(card.getTitle());
        _descriptionTextView.setText(card.getDescription());
        //TODO update level
        _levelTextView.setText("level 1");
    }

    private int getElementResource(Card card){
        switch (card.getElement()){
            case "earth":
                return R.drawable.ic_earth;
            case "fire":
                return R.drawable.ic_fire;
            case "water":
                return R.drawable.ic_water;
            case "air":
                return R.drawable.ic_air;
        }

        return 0;
    }
}
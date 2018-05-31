package ua.in.zeusapps.mediar.fragments;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.common.Layout;

@Layout(R.layout.fragment_promo_congrat)
public class PromoDescriptionFragment extends FragmentBase {

    private final static String ANIMAL_TYPE = "animal";
    private final static String MESSAGE = "message";
    private final static String VALID_DAYS = "days";

    @BindView(R.id.fragment_promo_animal_congratulation)
    TextView _animalName;
    @BindView(R.id.fragment_promo_details_message_congratulation)
    TextView _message;
    @BindView(R.id.valid_days_congratulation)
    TextView _validDays;

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        _animalName.setText(arguments.getString(ANIMAL_TYPE));
        _message.setText(arguments.getString(MESSAGE));
        _validDays.setText(arguments.getString(VALID_DAYS));
    }

    public static PromoDescriptionFragment newInstance(String animalType, String message, String validDays){
        PromoDescriptionFragment fragment = new PromoDescriptionFragment();

        Bundle bundle = new Bundle();

        bundle.putString(ANIMAL_TYPE, animalType);
        bundle.putString(MESSAGE, message);
        bundle.putString(VALID_DAYS, validDays);

        fragment.setArguments(bundle);

        return fragment;
    }
}

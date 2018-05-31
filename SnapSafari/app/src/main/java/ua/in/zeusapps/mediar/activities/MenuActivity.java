package ua.in.zeusapps.mediar.activities;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.common.Layout;

@Layout(R.layout.activity_main_menu)
public class MenuActivity extends ActivityBase {
    @BindView(R.id.activity_main_menu_profile_image)
    CircleImageView _profileImage;
    @BindView(R.id.activity_main_menu_play_button)
    Button _playButton;
    @BindView(R.id.activity_main_menu_snap_cards_button)
    Button _snapCardsButton;
    @BindView(R.id.activity_main_menu_settings)
    Button _settingsButton;
    @BindView(R.id.welcome_name)
    TextView _welcomName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = "Name goes here";
        _welcomName.setText("WelcomeBack\n" + name);
    }

    @OnClick(R.id.activity_main_menu_play_button)
    public void onPlay(){
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is disable", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, BlankActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.activity_main_menu_snap_cards_button)
    public void onSnapCards(){
        Intent intent = new Intent(this, SnapCardsActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.activity_main_menu_settings)
    public void onSettings() {

//        getSharedPreferences(App.TAG, MODE_APPEND)
//                .edit()
//                .putBoolean(App.REGISTERED, false)
//                .apply();
//
//        Intent intent = new Intent(this, SignInActivity.class);
//        startActivity(intent);
//        finish();
    }
}

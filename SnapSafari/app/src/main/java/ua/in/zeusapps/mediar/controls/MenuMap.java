package ua.in.zeusapps.mediar.controls;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import ua.in.zeusapps.mediar.R;

/**
 * Created by- slava on 13.01.18.
 */

public class MenuMap extends Menu {

    @BindView(R.id.menu_map)
    ImageButton _map;

    public MenuMap(Context context) {
        super(context);
    }

    public MenuMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuMap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuMap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLayoutStile() {
        inflater.inflate(R.layout.menu_map, this);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        super.setOnClickListener(listener);
        _map.setOnClickListener(listener);
    }
}

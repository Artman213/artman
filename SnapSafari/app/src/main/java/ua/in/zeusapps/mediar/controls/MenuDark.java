package ua.in.zeusapps.mediar.controls;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import ua.in.zeusapps.mediar.R;

/**
 * Created by- slava on 13.12.17.
 */

public class MenuDark extends Menu {

    public MenuDark(Context context) {
        super(context);
    }

    public MenuDark(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuDark(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuDark(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLayoutStile() {
        inflater.inflate(R.layout.menu_dark, this);
    }
}

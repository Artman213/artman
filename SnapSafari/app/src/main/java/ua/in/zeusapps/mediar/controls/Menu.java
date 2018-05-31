package ua.in.zeusapps.mediar.controls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.activities.MenuActivity;

public class Menu extends FrameLayout {

    protected IMenuBackPressedListener _backPressedListener;
    protected LayoutInflater inflater;

    @BindView(R.id.menu_hamburger)
    ImageButton _menuButton;
    @BindView(R.id.menu_back)
    ImageButton _backButton;
    @BindView(R.id.menu_title)
    TextView _titleTextView;

    public Menu(Context context) {
        super(context);
        init(null);
    }

    public Menu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Menu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Menu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void setLayoutStile(){
        inflater.inflate(R.layout.menu, this);
    }

    protected void init(AttributeSet attrs) {
        boolean isDark = false;

        inflater = LayoutInflater.from(getContext());
        setLayoutStile();

        ButterKnife.bind(this, this);

        _backPressedListener = new IMenuBackPressedListener() {
            @Override
            public void onBackPressed() {
                try {
                    // TODO open cards collection on back button press
//                    Intent intent = new Intent(getContext(), SnapCardsActivity.class);
//                    getContext().startActivity(intent);

                    //Finish activity
                    Activity activity = (Activity) getContext();
                    activity.finish();
                } catch (Throwable throwable) {
                    //
                }
            }
        };
        // TODO check set on menu button click
        _menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuActivity.class);
                getContext().startActivity(intent);
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        });

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Menu);
            int value = array.getInt(R.styleable.Menu_style, 0);
            isDark = value == 1;

            String title = array.getString(R.styleable.Menu_title);
            _titleTextView.setText(title);
        }

        if (!isDark) {
            return;
        }

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//        _titleTextView.setTextColor(Color.argb(250,250,250,250));
//        if (darkStyle){
//            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_dark_menu);
//            _menuButton.setImageBitmap(icon);
//        }
//        Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.menu_back);
//        _backButton.setImageBitmap(back);
    }

    @OnClick(R.id.menu_back)
    public void onBackPressed() {
        if (_backPressedListener != null) {
            _backPressedListener.onBackPressed();
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        _menuButton.setOnClickListener(listener);
    }

    public ImageButton getHamburgerButton() {
        return _menuButton;
    }

    public TextView getTitleTextView() {
        return _titleTextView;
    }

    public void setTitle(String title) {
        _titleTextView.setText(title);
    }

    public void setOnBackPressedListener(IMenuBackPressedListener listener) {
        _backPressedListener = listener;
    }

    public interface IMenuBackPressedListener {
        void onBackPressed();
    }
}

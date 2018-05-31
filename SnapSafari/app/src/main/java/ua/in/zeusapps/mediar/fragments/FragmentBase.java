package ua.in.zeusapps.mediar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ua.in.zeusapps.mediar.App;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.common.Layout;

public abstract class FragmentBase extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class cls = getClass();
        if (cls.isAnnotationPresent(Layout.class)){
            Layout layout = (Layout) cls.getAnnotation(Layout.class);
            View view = inflater.inflate(layout.value(), container, false);
            ButterKnife.bind(this, view);
            init();
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static boolean isTablet(Context context){
        if (context == null){
            return false;
        }
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    protected void init() {

    }

    public App getApp() {
        return (App) getActivity().getApplication();
    }
}

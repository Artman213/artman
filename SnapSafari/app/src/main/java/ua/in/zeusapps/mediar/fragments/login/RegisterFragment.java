package ua.in.zeusapps.mediar.fragments.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.activities.RegisterActivity;

/**
 * Created by Slava on 04.12.17.
 */

public class RegisterFragment extends Fragment {

    public static RegisterFragment getInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registre, container, false);

        Button gmail = (Button) view.findViewById(R.id.activity_login_gmail_login);
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

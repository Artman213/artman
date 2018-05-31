package ua.in.zeusapps.mediar.fragments.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.in.zeusapps.mediar.App;
import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.activities.MenuActivity;
import ua.in.zeusapps.mediar.models.Login;
import ua.in.zeusapps.mediar.models.Token;

import static android.content.Context.MODE_APPEND;

/**
 * Created by slava on 04.12.17.
 */

public class LogInFragment extends Fragment {
    OnSaveToken mCallback;

    private EditText name;
    private EditText password;
    private Button loginButton;
    private Button letsTry;

    private App app;

    public static LogInFragment getInstance() {
        Bundle args = new Bundle();
        LogInFragment fragment = new LogInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnSaveToken {
        void onSaveToken(String token);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnSaveToken) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);

        return view;
    }

    private void initUI(View view) {
        name = (EditText) view.findViewById(R.id.fragment_login_email);
        password = (EditText) view.findViewById(R.id.fragment_login_password);

        loginButton = (Button) view.findViewById(R.id.fragment_login_lets_start);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

    }

    public void onLogin() {

        Login login = new Login(name.getText().toString(), password.getText().toString());
        app = (App) getActivity().getApplication();

        app.getService()
                .getToken(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Token>() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void accept(Token token) throws Exception {
                                mCallback.onSaveToken(token.getKey());

                                app.getSharedPreferences(App.TAG, MODE_APPEND)
                                        .edit()
                                        .putBoolean(App.REGISTERED, true)
                                        .apply();

                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_SHORT).show();
                            }
                        });

    }

}

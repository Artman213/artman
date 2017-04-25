package com.example.artman2111.creditcards.appSetingsAndConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by artman2111 on 07.02.17.
 */

public class AppSettings {
    private static final String KEY_BOOLEAN_IS_TERMS_ACCEPT = "KEY_BOOLEAN_IS_TERMS_ACCEPT";
    private static final String KEY_BOOLEAN_IS_CLOSE_ACTIVITY = "KEY_BOOLEAN_IS_CLOSE_ACTIVITY";

    private SharedPreferences m_SharedPreferences;

    public AppSettings(Context context){
        m_SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isTermsAccept(){
        return m_SharedPreferences.getBoolean(KEY_BOOLEAN_IS_TERMS_ACCEPT, false);
    }

    public void setIsTermsAccept(boolean bIsTermsAccept){
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putBoolean(KEY_BOOLEAN_IS_TERMS_ACCEPT, bIsTermsAccept);
        editor.commit();
    }

    public boolean isCloseActivity(){
        return m_SharedPreferences.getBoolean(KEY_BOOLEAN_IS_CLOSE_ACTIVITY, false);
    }

    public void setIsCloseActivity(boolean bIsCloseActivity){
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putBoolean(KEY_BOOLEAN_IS_CLOSE_ACTIVITY, bIsCloseActivity);
        editor.commit();
    }
}

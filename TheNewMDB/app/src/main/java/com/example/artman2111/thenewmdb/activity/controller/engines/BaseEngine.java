package com.example.artman2111.thenewmdb.activity.controller.engines;

import android.content.Context;

/**
 * Created by artman2111 on 16.05.17.
 */

public class BaseEngine {

    private Context m_Context;

    public BaseEngine(Context context){
        m_Context = context;
    }

    protected Context getContext(){
        return m_Context;
    }
}

package com.example.artman2111.creditcards.controller.engines;

import android.content.Context;

import com.example.artman2111.creditcards.controller.wrapersdb.CardInfoDBWraper;
import com.example.artman2111.creditcards.models.CardInfo;

import java.util.ArrayList;

/**
 * Created by artman2111 on 09.02.17.
 */

public class CardInfoEngine extends BaseEngine {
    public CardInfoEngine(Context context) {
        super(context);
    }
    public ArrayList<CardInfo> getAll(){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        return dbWrapper.getAll();
    }

    public CardInfo getUserById(long nId){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        return dbWrapper.getUserById(nId);
    }

    public ArrayList<CardInfo> getUserByField(String strSearch){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        return dbWrapper.getUserByField(strSearch);
    }

    public void update(CardInfo item){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        dbWrapper.update(item);
    }

    public void insert(CardInfo item){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        dbWrapper.insert(item);
    }

    public void remove(CardInfo item){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        dbWrapper.remove(item);
    }

    public void removeById(long nId){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        dbWrapper.removeById(nId);
    }

    public void removeAll(){
        CardInfoDBWraper dbWrapper = new CardInfoDBWraper(getContext());
        dbWrapper.removeAll();
    }
}

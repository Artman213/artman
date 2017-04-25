package com.example.artman2111.creditcards.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.artman2111.creditcards.R;
import com.example.artman2111.creditcards.appSetingsAndConstants.AppSettings;
import com.example.artman2111.creditcards.appSetingsAndConstants.DBConstants;
import com.example.artman2111.creditcards.controller.engines.CardInfoEngine;
import com.example.artman2111.creditcards.db.DB;
import com.example.artman2111.creditcards.models.CardInfo;

/**
 * Created by artman2111 on 06.02.17.
 */

public class EditCardActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_USER_ID = "KEY_USER_ID";
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextCard1;
    private EditText editTextCard2;
    private long m_nUserId = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);

        Intent intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                m_nUserId = bundle.getLong(KEY_USER_ID, -1);
            }
        }

        editTextName = (EditText) findViewById(R.id.name_activityEdit);
        editTextSurname = (EditText) findViewById(R.id.surname_activityEdit);
        editTextCard1 = (EditText) findViewById(R.id.card1_activityEdit);
        editTextCard2 = (EditText) findViewById(R.id.card2_activityEdit);

        Button btnAdd = (Button) findViewById(R.id.ok_activityEdit);
        btnAdd.setOnClickListener(this);

        if (m_nUserId>=0){
            btnAdd.setText(R.string.update);
            Button btnRemove = (Button) findViewById(R.id.delete_activityEdit);
            btnRemove.setOnClickListener(this);
            btnRemove.setVisibility(View.VISIBLE);

            CardInfoEngine cardInfoEngine = new CardInfoEngine(this);
            CardInfo cardInfo = cardInfoEngine.getUserById(m_nUserId);
            editTextName.setText(cardInfo.getName());
            editTextSurname.setText(cardInfo.getSName());
            editTextCard1.setText(cardInfo.getCard1());
            editTextCard2.setText(cardInfo.getCard2());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok_activityEdit:
                CardInfo cardInfo = new CardInfo(editTextName.getText().toString(),
                        editTextSurname.getText().toString(),
                        editTextCard1.getText().toString(),
                        editTextCard2.getText().toString());
                if (cardInfo.validate()){
                    CardInfoEngine cardInfoEngine = new CardInfoEngine(this);
                    if(m_nUserId>=0){
                        cardInfo.setId(m_nUserId);
                        cardInfoEngine.update(cardInfo);
                    } else {
                        cardInfoEngine.insert(cardInfo);
                    }

                    if(m_nUserId>=0) {
                        finish();
                    } else {
                        AppSettings settings = new AppSettings(this);
                        if (settings.isCloseActivity()){
                            finish();
                        } else {
                            finish();
                        }
                    }
                }
                break;
            case R.id.delete_activityEdit:
                CardInfoEngine cardInfoEngine = new CardInfoEngine(this);
                cardInfoEngine.removeById(m_nUserId);
                finish();
                break;
        }
    }

}

package com.example.artman2111.creditcards.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.example.artman2111.creditcards.R;
import com.example.artman2111.creditcards.adapter.AdapterForUserInfo;
import com.example.artman2111.creditcards.models.CardInfo;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private AdapterForUserInfo adapterForUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler;
        handler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText searchEditText = (EditText) findViewById(R.id.searchEditText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapterForUserInfo.getFilter().filter(editable);
            }
        });
        ListView listView = (ListView) findViewById(R.id.listView);
        adapterForUserInfo = new AdapterForUserInfo(this);
        listView.setAdapter(adapterForUserInfo);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CardInfo userInfo = (CardInfo) adapterView.getAdapter().getItem(i);
                openAddActivity(userInfo.getId());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterForUserInfo.updateList();
    }

    private void openAddActivity(long nId) {
        Intent intent = new Intent(this, EditCardActivity.class);
        intent.putExtra(EditCardActivity.KEY_USER_ID, nId);
        startActivity(intent);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, EditCardActivity.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

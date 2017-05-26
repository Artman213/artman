package com.example.artman2111.test.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.artman2111.test.R;
import com.example.artman2111.test.adapter.UserAdapter;
import com.example.artman2111.test.connectionnetwork.UserAccept;
import com.example.artman2111.test.db.DataBase;
import com.example.artman2111.test.db.DataBaseConstans;
import com.example.artman2111.test.models.UserList;

import java.util.ArrayList;

/**
 * Created by artman2111 on 26.05.17.
 */

public class UserActivity extends AppCompatActivity {
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<UserList> userLists;
    public static boolean inData;
    private DataBase dataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
         dataBase = new DataBase(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewInUserActivity);
        if (inData){
            userLists = new ArrayList<>();
            userLists.addAll(getAll());
            adapter = new UserAdapter(UserActivity.this,userLists);
            recyclerView.setAdapter(adapter);
        }else {
            removeAll();
            userList();
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void userList(){

        userLists = new ArrayList<>();
        Thread thread;
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                UserAccept userAccept = new UserAccept(UserActivity.this);
                userLists = (ArrayList<UserList>) userAccept.getPathsFromAPI();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0;i<userLists.size();i++){
                            DataBase dataBase = new DataBase(UserActivity.this);
                            String username = userLists.get(i).getUsername();
                            String lastlogin = userLists.get(i).getLast_login();
                            dataBase.putInDB(username,lastlogin);
                        }
                        adapter = new UserAdapter(UserActivity.this,userLists);
                        recyclerView.setAdapter(adapter);
                    }

                });
            }
        });
        thread.start();
    }
    public ArrayList<UserList> getAll(){
        ArrayList<UserList> arrResult = new ArrayList<>();
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor cursor = db.query(DataBaseConstans.DB_V1.TABLE_USER_INFO.TABLE_NAME
                , null, null, null, null, null, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    UserList userlist = new UserList(cursor);
                    arrResult.add(userlist);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return arrResult;
    }
    public void removeAll(){
        SQLiteDatabase db = dataBase.getWritableDatabase();

        db.delete(DataBaseConstans.DB_V1.TABLE_USER_INFO.TABLE_NAME,null,null);

        db.close();
    }

}

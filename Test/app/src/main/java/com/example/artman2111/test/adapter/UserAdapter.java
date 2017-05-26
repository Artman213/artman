package com.example.artman2111.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artman2111.test.R;
import com.example.artman2111.test.models.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artman2111 on 26.05.17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context = null;
    private LayoutInflater inflater = null;
    private List<UserList> arrayUsers = new ArrayList<>();

    public UserAdapter(Context context,List<UserList> arrayUsers){
        inflater = LayoutInflater.from(context);
        this.arrayUsers = arrayUsers;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_user, parent, false);
        return new ItemView(itemView);
    }

    private class ItemView extends RecyclerView.ViewHolder{
        View rootView;
        TextView username,lastlogin;
        private ItemView(View itemView) {
            super(itemView);
            rootView = itemView;
            username = (TextView) rootView.findViewById(R.id.userName);
            lastlogin = (TextView) rootView.findViewById(R.id.lastLogin);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemView) holder).username.setText(arrayUsers.get(position).getUsername());
        ((ItemView) holder).lastlogin.setText(arrayUsers.get(position).getLast_login());

    }

    @Override
    public int getItemCount() {
        return arrayUsers.size();
    }
}

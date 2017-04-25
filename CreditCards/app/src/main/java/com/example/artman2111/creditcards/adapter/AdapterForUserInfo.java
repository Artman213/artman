package com.example.artman2111.creditcards.adapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artman2111.creditcards.R;
import com.example.artman2111.creditcards.controller.engines.CardInfoEngine;
import com.example.artman2111.creditcards.models.CardInfo;

import java.util.List;

/**
 * Created by artman2111 on 09.02.17.
 */

public class AdapterForUserInfo extends BaseAdapter implements Filterable  {
    private List<CardInfo> cardInfos;
    private CardInfoEngine engine;
    private LayoutInflater inflater;
    private Context context;


    public AdapterForUserInfo(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        engine = new CardInfoEngine(context);
        cardInfos = engine.getAll();
    }

    @Override
    public int getCount() {
        return cardInfos.size();
    }

    @Override
    public CardInfo getItem(int i) {
        return cardInfos.isEmpty() ? null : cardInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final CardInfo cardInfo = getItem(i);
        final ViewHolder holder;
        View itemView;
        if (view == null) {
            holder = new ViewHolder();
            itemView = inflater.inflate(R.layout.list_user, viewGroup, false);
            holder.nameTextView = (TextView) itemView.findViewById(R.id.textViewName);
            holder.card1TextView = (TextView) itemView.findViewById(R.id.textViewCard);
            holder.nameBtn = (Button) itemView.findViewById(R.id.btnCopy);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            itemView = view;
        }
        holder.nameTextView.setText(cardInfo.getName() + " " + cardInfo.getSName());
        String card = cardInfo.getCard1();
        card = card.replaceAll("(.{4})", "$1 ");
        holder.card1TextView.setText("Card1: " + card);
        holder.nameBtn.findViewById(R.id.btnCopy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String card1 = cardInfo.getCard1();
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", card1);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(view.getContext(), card1 +" Copy", Toast.LENGTH_SHORT).show();
            }
        });
        return itemView;
    }

    public void updateList() {
        cardInfos = engine.getAll();
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<CardInfo> userInfoList = engine.getUserByField(charSequence.toString());
                FilterResults filterResults = new FilterResults();
                filterResults.count = userInfoList.size();
                filterResults.values = userInfoList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                cardInfos = (List<CardInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    private class ViewHolder {
        TextView nameTextView;
        TextView card1TextView;
        Button nameBtn;
    }
}

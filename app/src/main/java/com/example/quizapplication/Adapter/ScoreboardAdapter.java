package com.example.quizapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizapplication.R;
import java.util.ArrayList;

public class ScoreboardAdapter extends BaseAdapter {

    private Context context;
    public LayoutInflater inflater;

    public ArrayList<String> playerName;
    public ArrayList<String> playerScore;

    public ScoreboardAdapter(Context context, ArrayList<String> playerName, ArrayList<String> playerScore){
        this.context = context;
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    @Override
    public int getCount() {
        return playerName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final viewHolder holder;

        if(view == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listscore_textlayout, null);
            holder = new viewHolder();
            holder.playerName = (TextView) view.findViewById(R.id.playerName);
            holder.playerScore = (TextView) view.findViewById(R.id.playerScore);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }

        holder.playerName.setText(playerName.get(position));
        holder.playerScore.setText(playerScore.get(position));
        return view;
    }

    public class viewHolder {
        TextView playerName;
        TextView playerScore;
    }
}

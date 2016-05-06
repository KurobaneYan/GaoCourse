package com.kurobane.yan.gao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GoalsAdapter extends ArrayAdapter<Goal> {
    public GoalsAdapter(Context context, List<Goal> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goal goal = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goal, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.goal_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.goal_description);

        tvName.setText(goal.getName());
        tvHome.setText(goal.getDescription());

        return convertView;
    }

}

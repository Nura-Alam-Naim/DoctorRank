package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ScheduleListAdapter extends ArrayAdapter<ScheduleItem> {

    private final Context context;
    private final ArrayList<ScheduleItem> items;

    public ScheduleListAdapter(@NonNull Context context, @NonNull ArrayList<ScheduleItem> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_schedule_slot, parent, false);

        TextView tvDay = convertView.findViewById(R.id.tvDay);
        TextView tvSlot = convertView.findViewById(R.id.tvSlot);

        ScheduleItem item = items.get(position);

        if (item.isDayHeader) {
            tvDay.setVisibility(View.VISIBLE);
            tvSlot.setVisibility(View.GONE);
            tvDay.setText(item.text);

        } else {
            tvDay.setVisibility(View.GONE);
            tvSlot.setVisibility(View.VISIBLE);
            tvSlot.setText(item.text);
        }

        return convertView;
    }
}

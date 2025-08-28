package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AppointmentListAdapter extends ArrayAdapter<Booking> {

    private final Context context;
    private final ArrayList<Booking> values;

    public AppointmentListAdapter(@NonNull Context context, @NonNull ArrayList<Booking> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_appoinments, parent, false);
        }

        Booking booking = values.get(position);

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvTime = convertView.findViewById(R.id.tvTime);
        TextView tvDName = convertView.findViewById(R.id.tvDName);
        TextView tvSpeciality = convertView.findViewById(R.id.tvSpeciality);

        tvDate.setText(booking.date);
        tvTime.setText(booking.time);
        tvDName.setText(booking.doctorName);
        tvSpeciality.setText(booking.specialization);

        return convertView;
    }
}

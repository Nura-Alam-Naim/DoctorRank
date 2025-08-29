package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AppointmentListAdapter extends ArrayAdapter<UserBooking> {

    private final Context context;
    private final ArrayList<UserBooking> values;

    public AppointmentListAdapter(@NonNull Context context, @NonNull ArrayList<UserBooking> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_appoinments, parent, false);

        UserBooking booking = values.get(position);

        TextView tvDate = rowView.findViewById(R.id.tvDate);
        TextView tvTime = rowView.findViewById(R.id.tvTime);
        TextView tvDName = rowView.findViewById(R.id.tvDName);
        TextView tvSpeciality = rowView.findViewById(R.id.tvSpeciality);

        tvDate.setText(booking.date);
        tvTime.setText(booking.time);
        tvDName.setText(booking.doctorName);
        tvSpeciality.setText(booking.specialization);

        return rowView;
    }
}

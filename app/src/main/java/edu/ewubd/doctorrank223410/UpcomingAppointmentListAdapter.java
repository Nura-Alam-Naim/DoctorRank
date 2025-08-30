package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UpcomingAppointmentListAdapter extends ArrayAdapter<UserBooking> {

    private final Context context;
    private final ArrayList<UserBooking> values;

    public UpcomingAppointmentListAdapter(@NonNull Context context, @NonNull ArrayList<UserBooking> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_upcomingappoinments, parent, false);

        UserBooking booking = values.get(position);

        TextView tvDate = rowView.findViewById(R.id.tvDate);
        TextView tvTime = rowView.findViewById(R.id.tvTime);
        TextView tvDName = rowView.findViewById(R.id.tvDName);
        TextView tvSpeciality = rowView.findViewById(R.id.tvSpeciality);
        Button btCancel = rowView.findViewById(R.id.btCancel);

        tvDate.setText(booking.date);
        tvTime.setText(booking.time);
        tvDName.setText(booking.doctorName);
        tvSpeciality.setText(booking.specialization);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CancelConfirmation.class);
                intent.putExtra("doctorId", booking.doctorId);
                intent.putExtra("appointmentDate", booking.date);
                intent.putExtra("appointmentTime", booking.time);
                intent.putExtra("doctorName", booking.doctorName);
                intent.putExtra("specialization", booking.specialization);
                intent.putExtra("bookingKey", booking.bookingKey);
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}

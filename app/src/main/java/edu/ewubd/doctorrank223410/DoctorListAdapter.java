package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class DoctorListAdapter extends ArrayAdapter<T_DoctorInfo> {

    private final Context context;
    private final ArrayList<T_DoctorInfo> values;

    public DoctorListAdapter(@NonNull Context context, @NonNull ArrayList<T_DoctorInfo> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.doctor_adapter_row, parent, false);

         T_DoctorInfo doctor = values.get(position);

        // Bind XML views
        TextView doctorName = rowView.findViewById(R.id.doctorName);
        TextView doctorInfo = rowView.findViewById(R.id.doctorInfo);
        TextView doctorRoom = rowView.findViewById(R.id.doctorRoom);
        RatingBar doctorRating = rowView.findViewById(R.id.doctorRating);
        ImageView doctorPhoto = rowView.findViewById(R.id.doctorPhoto);

        Button profileBtn = rowView.findViewById(R.id.profile);
        Button bookNowBtn = rowView.findViewById(R.id.bookNow);

        // Set values
        doctorName.setText(doctor.name);
        doctorInfo.setText(doctor.speciality);
        doctorRoom.setText("Room No: " + doctor.roomNo);
        doctorRating.setRating(doctor.rating);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, DoctorProfilePage.class);
            intent.putExtra("doctorId", doctor.name); // pass doctor info
            context.startActivity(intent);
        });

        // Book Now button action
        bookNowBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, DateSelection.class);
            intent.putExtra("doctorId", doctor.name);
            intent.putExtra("doctorName", doctor.speciality);
            context.startActivity(intent);
        });
        return rowView;
    }
}

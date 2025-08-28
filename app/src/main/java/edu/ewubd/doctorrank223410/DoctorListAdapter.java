package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;


public class DoctorListAdapter extends ArrayAdapter<T_DoctorInfo> {

    private final Context context;
    private final ArrayList<T_DoctorInfo> values;
    private final ArrayList<T_DoctorInfo> allValues;

    public DoctorListAdapter(@NonNull Context context, @NonNull ArrayList<T_DoctorInfo> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
        this.allValues = new ArrayList<>(items); // keep a full copy
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.doctor_adapter_row, parent, false);

        T_DoctorInfo doctor = values.get(position);

        TextView doctorName = rowView.findViewById(R.id.doctorName);
        TextView doctorSpeciality = rowView.findViewById(R.id.doctorSpeciality);
        TextView doctorRoom = rowView.findViewById(R.id.doctorRoom);
        RatingBar doctorRating = rowView.findViewById(R.id.doctorRating);
        ImageView doctorPhoto = rowView.findViewById(R.id.doctorPhoto);
        TextView doctorCharge = rowView.findViewById(R.id.doctorCharge);

        Button profileBtn = rowView.findViewById(R.id.profile);
        Button bookNowBtn = rowView.findViewById(R.id.bookNow);

        // Set values
        doctorName.setText(doctor.name);
        doctorSpeciality.setText(doctor.speciality);
        doctorRoom.setText("Room No: " + doctor.roomNo);
        doctorRating.setRating(doctor.rating);
        doctorCharge.setText("Charge: " + doctor.charge);

        if (doctor.picture != null && !doctor.picture.isEmpty()) {
            try {
                byte[] bytes = Base64.decode(doctor.picture, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                doctorPhoto.setImageBitmap(bmp);
            } catch (Exception e) {
                doctorPhoto.setImageResource(R.drawable.dummy);
            }
        }

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DoctorProfilePage.class);
            intent.putExtra("doctorId", doctor.id);
            context.startActivity(intent);
        });

        bookNowBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DateSelection.class);
            intent.putExtra("doctorId", doctor.id);
            intent.putExtra("doctorName", doctor.name);
            context.startActivity(intent);
        });

        return rowView;
    }
    @Override
    public Filter getFilter() {
        return doctorFilter;
    }
    private Filter doctorFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<T_DoctorInfo> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allValues);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (T_DoctorInfo doctor : allValues) {
                    if (doctor.name.toLowerCase().contains(filterPattern) ||
                            doctor.speciality.toLowerCase().contains(filterPattern)) {
                        filteredList.add(doctor);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            values.clear();
            values.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}


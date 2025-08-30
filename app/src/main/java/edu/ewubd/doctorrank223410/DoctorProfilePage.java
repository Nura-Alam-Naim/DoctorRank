package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DoctorProfilePage extends AppCompatActivity {

    private ImageView ivDoctorPicture;
    private TextView tvDoctorName, tvDoctorQualification, tvDoctorRating, tvDoctorBMDCno, tvDoctorCharge;
    private Button btBack, btBookNow;
    private ListView lvSchedule;  // NEW – to show schedule
    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        if (i != null && i.hasExtra("doctorId"))
            doctorId = i.getStringExtra("doctorId");

        setContentView(R.layout.activity_doctor_profile_page);

        // UI references
        ivDoctorPicture = findViewById(R.id.ivDoctorPicture);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvDoctorQualification = findViewById(R.id.tvDoctorQualification);
        tvDoctorRating = findViewById(R.id.tvDoctorRating);
        tvDoctorBMDCno = findViewById(R.id.tvDoctorBMDCno);
        tvDoctorCharge = findViewById(R.id.tvDoctorCharge);
        btBack = findViewById(R.id.btBack);
        btBookNow = findViewById(R.id.btBookNow);
        lvSchedule = findViewById(R.id.lvSchedule); // NEW

        // Fetch doctor from DB
        T_DoctorInfo doctor = DoctorsDB.get(this).getById(doctorId);

        if (doctor != null) {
            tvDoctorName.setText(doctor.name);
            tvDoctorQualification.setText(doctor.speciality);
            tvDoctorRating.setText(String.valueOf(doctor.rating));
            tvDoctorBMDCno.setText(doctor.BDMC);
            tvDoctorCharge.setText("Charge: " + String.valueOf(doctor.charge));

            // Show picture
            if (doctor.picture != null && !doctor.picture.isEmpty()) {
                try {
                    byte[] bytes = Base64.decode(doctor.picture, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ivDoctorPicture.setImageBitmap(bmp);
                } catch (Exception e) {
                    ivDoctorPicture.setImageResource(R.drawable.dummy);
                }
            }

            ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
            if (doctor.schedule != null && !doctor.schedule.isEmpty()) {
                for (String day : doctor.schedule.keySet()) {
                    // Add day header
                    scheduleItems.add(new ScheduleItem(day, true));

                    // Add slots
                    List<String> slotList = doctor.schedule.get(day);
                    if (slotList != null) {
                        ArrayList<String> slots = new ArrayList<>(slotList);
                        for (String slot : slots) {
                            scheduleItems.add(new ScheduleItem(" - " + slot, false));
                        }
                    }
                }
            }

            // Attach adapter
            ScheduleListAdapter adapter = new ScheduleListAdapter(this, scheduleItems);
            lvSchedule.setAdapter(adapter);

        } else {
            Toast.makeText(this, "Doctor Fetching Failed", Toast.LENGTH_SHORT).show();
        }

        // Back button
        btBack.setOnClickListener(v -> finish());

        // Book Now button
        btBookNow.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getUid();
            if(userId == null) {
                Toast.makeText(this,"Please Login First",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DoctorProfilePage.this, LoginPage.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(DoctorProfilePage.this, DateSelection.class);
                intent.putExtra("doctorId", doctor.id);
                intent.putExtra("doctorName", doctor.name);
                startActivity(intent);
            }
        });
    }
}

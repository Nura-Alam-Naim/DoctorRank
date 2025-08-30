package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CancelConfirmation extends AppCompatActivity {

    private String doctorId, userId, bookingKey, appointmentDate, appointmentTime, doctorName, specialization;
    private TextView tvAppointmentDate, tvAppointmentTime, tvDoctorName, tvSpecialization;
    Button btconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_confirmation);

        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        bookingKey = intent.getStringExtra("bookingKey");
        appointmentDate = intent.getStringExtra("appointmentDate");
        appointmentTime = intent.getStringExtra("appointmentTime");
        doctorName = intent.getStringExtra("doctorName");
        specialization = intent.getStringExtra("specialization");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvSpecialization = findViewById(R.id.tvSpecialization);

        tvAppointmentDate.setText(appointmentDate);
        tvAppointmentTime.setText(appointmentTime);
        tvDoctorName.setText(doctorName);
        tvSpecialization.setText(specialization);

        Button btconfirm = findViewById(R.id.Confirm);

        btconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBooking();
                finish();
            }
        });
    }

    private void cancelBooking() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("bookings").child(doctorId).child(appointmentDate).child(appointmentTime)
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        db.child("user_bookings").child(userId).child(bookingKey).removeValue()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(CancelConfirmation.this, "Booking Cancelled ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CancelConfirmation.this, UpcomingAppointment.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(CancelConfirmation.this, "Failed to cancel booking from user side", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(CancelConfirmation.this, "Failed to cancel booking from doctor side", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

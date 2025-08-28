package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class confirmation extends AppCompatActivity {

    private TextView  tvAppointmentDate, tvAppointmentTime, tvDoctorName, tvSpecialization,  tvRoomNumber ;
    private Button btHome ;
    private String doctorId, doctorName, doctorSpecialization, doctorRoomNumber, doctorDate, doctorTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvSpecialization = findViewById(R.id.tvSpecialization);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        btHome = findViewById(R.id.btHome);

        Intent i = getIntent();
        if (i != null) {
            doctorId = i.getStringExtra("doctorId");
            doctorName = i.getStringExtra("doctorName");
            doctorSpecialization = i.getStringExtra("doctorSpecialization");
            doctorRoomNumber = i.getStringExtra("doctorRoomNumber");
            doctorDate = i.getStringExtra("appointmentDate");
            doctorTime = i.getStringExtra("appointmentTime");
        }
        tvAppointmentDate.setText(doctorDate);
        tvAppointmentTime.setText(doctorTime);
        tvDoctorName.setText(doctorName);
        tvSpecialization.setText(doctorSpecialization);
        tvRoomNumber.setText(doctorRoomNumber);

        btHome.setOnClickListener(v -> {
            Intent intent = new Intent(confirmation.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
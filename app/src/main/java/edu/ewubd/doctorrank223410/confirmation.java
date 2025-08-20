package edu.ewubd.doctorrank223410;

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

    private ImageView ivLogo ;
    private TextView tvSuccessMessage, tvAppointmentDate, tvAppointmentTime, tvDoctorName, tvSpecialization, tvPosition, tvExperience, tvAddress, tvRoomNumber ;
    private Button btHome ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        ivLogo = findViewById(R.id.ivLogo) ;

        tvSuccessMessage = findViewById(R.id.tvSuccessMessage) ;
        tvAppointmentDate = findViewById(R.id.tvAppointmentDate) ;
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime) ;
        tvDoctorName = findViewById(R.id.tvDoctorName) ;
        tvSpecialization = findViewById(R.id.tvSpecialization) ;
        tvPosition = findViewById(R.id.tvPosition) ;
        tvExperience = findViewById(R.id.tvExperience) ;
        tvAddress = findViewById(R.id.tvAddress) ;
        tvRoomNumber = findViewById(R.id.tvRoomNumber) ;

        btHome = findViewById(R.id.btHome) ;

    }
}
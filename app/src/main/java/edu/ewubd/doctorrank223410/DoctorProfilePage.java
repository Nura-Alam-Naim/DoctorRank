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

public class DoctorProfilePage extends AppCompatActivity {

    private ImageView ivDoctorPicture ;
    private TextView tvDoctorName, tvDoctorQualification, tvDoctorRating, tvDoctorBMDCno, tvDoctorVisitingHours, tvDoctorCharge;
    private Button btBack, btBookNow ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_page);

        ivDoctorPicture = findViewById(R.id.ivDoctorPicture) ;

        tvDoctorName = findViewById(R.id.tvDoctorName) ;
        tvDoctorQualification = findViewById(R.id.tvDoctorQualification) ;
        tvDoctorRating = findViewById(R.id.tvDoctorRating) ;
        tvDoctorBMDCno = findViewById(R.id.tvDoctorBMDCno) ;
        tvDoctorVisitingHours = findViewById(R.id.tvDoctorVisitingHours) ;
        tvDoctorCharge = findViewById(R.id.tvDoctorCharge) ;

        btBack = findViewById(R.id.btBack) ;
        btBookNow = findViewById(R.id.btBookNow) ;


    }
}
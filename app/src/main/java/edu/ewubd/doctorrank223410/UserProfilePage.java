package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfilePage extends AppCompatActivity {

    private ImageView ivProfilePicture ;
    private TextView tvName, tvAge, tvHeight, tvWeight, tvPhone, tvEmail, tvAddress;
    private CheckBox cbMale, cbFemale;
    private Button btHome, btEditProfile, btAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_page);

        ivProfilePicture = findViewById(R.id.ivProfilePicture) ;

        tvName = findViewById(R.id.tvName) ;
        tvAge = findViewById(R.id.tvAge) ;
        tvHeight = findViewById(R.id.tvHeight) ;
        tvWeight = findViewById(R.id.tvWeight) ;
        tvPhone = findViewById(R.id.tvPhone) ;
        tvEmail = findViewById(R.id.tvEmail) ;
        tvAddress = findViewById(R.id.tvAddress) ;

        cbMale = findViewById(R.id.cbMale) ;
        cbFemale = findViewById(R.id.cbFemale) ;

        btHome = findViewById(R.id.btHome) ;
        btEditProfile = findViewById(R.id.btEditProfile) ;
        btAppointment = findViewById(R.id.btAppointment) ;



    }
}
package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PatientProfileEditPage extends AppCompatActivity {

    private ImageView ivProfilePicture ;
    private EditText etName, etAge, etHeight, etWeight, etPhone, etEmail, etAddress;
    private CheckBox cbMale, cbFemale ;
    private Button btBack, btHome, btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_edit_page);

        ivProfilePicture = findViewById(R.id.ivProfilePicture) ;

        etName = findViewById(R.id.etName) ;
        etAge = findViewById(R.id.etAge) ;
        etHeight = findViewById(R.id.etHeight) ;
        etWeight = findViewById(R.id.etWeight) ;
        etPhone = findViewById(R.id.etPhone) ;
        etEmail = findViewById(R.id.etEmail) ;
        etAddress = findViewById(R.id.etAddress) ;

        cbMale = findViewById(R.id.cbMale) ;
        cbFemale = findViewById(R.id.cbFemale) ;

        btBack = findViewById(R.id.btBack) ;
        btHome = findViewById(R.id.btHome) ;
        btSave = findViewById(R.id.btSave) ;
    }
}
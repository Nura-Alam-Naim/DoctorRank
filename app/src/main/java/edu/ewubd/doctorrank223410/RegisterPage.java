package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterPage extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etRePassword;
    private CheckBox cbMale, cbFemale;
    private Button btCreateAccount, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        etName = findViewById(R.id.etName) ;
        etEmail = findViewById(R.id.etEmail) ;
        etPhone = findViewById(R.id.etPhone) ;
        etPassword = findViewById(R.id.etPassword) ;
        etRePassword = findViewById(R.id.etRePassword) ;

        cbMale = findViewById(R.id.cbMale) ;
        cbFemale = findViewById(R.id.cbFemale) ;

        btCreateAccount = findViewById(R.id.btCreateAccount) ;
        btLogin = findViewById(R.id.btLogin) ;


    }
}
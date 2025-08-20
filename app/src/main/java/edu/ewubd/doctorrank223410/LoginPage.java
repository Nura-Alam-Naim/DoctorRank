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

public class LoginPage extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbRememberLogin, cbRememberPass;
    private Button btLogin, btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etUsername = findViewById(R.id.etUsername) ;
        etPassword = findViewById(R.id.etPassword) ;

        cbRememberLogin = findViewById(R.id.cbRememberLogin) ;
        cbRememberPass = findViewById(R.id.cbRememberPass) ;

        btLogin = findViewById(R.id.btLogin) ;
        btRegister = findViewById(R.id.btRegister) ;










    }
}
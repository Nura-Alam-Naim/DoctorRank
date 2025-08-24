package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private CheckBox cbRememberLogin, cbRememberPass;
    private Button btLogin, btRegister;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        boolean rememberLogin = sp.getBoolean("rememberLogin", false);
        boolean rememberPass = sp.getBoolean("rememberPass", false);
        if (rememberLogin)
        {
            return;
        }
        setContentView(R.layout.activity_login_page);
        etEmail = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberLogin = findViewById(R.id.cbRememberLogin);
        cbRememberPass = findViewById(R.id.cbRememberPass);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        if(rememberPass)
        {
            etPassword.setText(sp.getString("password", ""));
            etEmail.setText(sp.getString("email", ""));
        }

        String enteredEmail = etEmail.getText().toString().trim();
        String enteredPassword = etPassword.getText().toString().trim();

        String storedPassword = sp.getString("password_" + enteredEmail, null);
        String storedEmail = sp.getString("email_" + enteredEmail, null);

        btLogin.setOnClickListener(v -> {
            if (storedEmail != null && storedPassword != null && storedPassword.equals(enteredPassword)) {
                SharedPreferences.Editor editor = sp.edit();
                if (cbRememberLogin.isChecked()) {
                    editor.putBoolean("rememberLogin", true);
                    editor.putString("email", enteredEmail);
                    editor.putString("password", enteredPassword);
                } else {
                    editor.putBoolean("rememberLogin", false);
                }
                if (cbRememberPass.isChecked()) {
                    editor.putBoolean("rememberPass", true);
                    editor.putString("password", enteredPassword);
                    editor.putString("email", enteredEmail);
                } else {
                    editor.putBoolean("rememberPass", false);
                }
                editor.apply();

                startActivity(new Intent(LoginPage.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginPage.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
        btRegister.setOnClickListener(v -> {
            Intent i = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(i);
            finish();
        });
    }
}

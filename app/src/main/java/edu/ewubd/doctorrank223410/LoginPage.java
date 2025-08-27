package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private CheckBox cbRememberLogin, cbRememberPass;
    private Button btLogin, btRegister;
    SharedPreferences sp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // already logged in, skip login UI
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        sp = getSharedPreferences("login", MODE_PRIVATE);
        boolean rememberLogin = sp.getBoolean("rememberLogin", false);
        if (rememberLogin) {
            String email = sp.getString("email", "");
            String password = sp.getString("password", "");
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginPage.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            return;
        }

        setContentView(R.layout.activity_login_page);
        etEmail = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberLogin = findViewById(R.id.cbRememberLogin);
        cbRememberPass = findViewById(R.id.cbRememberPass);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);

        boolean rememberPass = sp.getBoolean("rememberPass", false);
        if (rememberPass) {
            etPassword.setText(sp.getString("password", ""));
            etEmail.setText(sp.getString("email", ""));
        }

        String enteredEmail = etEmail.getText().toString().trim();
        String enteredPassword = etPassword.getText().toString().trim();

        btLogin.setOnClickListener(v -> {
            mAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(LoginPage.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sp.edit();
                    if (cbRememberLogin.isChecked() || cbRememberPass.isChecked()) {
                        editor.putBoolean("rememberLogin", cbRememberLogin.isChecked());
                        editor.putBoolean("rememberPass", cbRememberPass.isChecked());
                        editor.putString("email", enteredEmail);
                        editor.putString("password", enteredPassword);
                        editor.apply();
                    } else {
                        editor.putBoolean("rememberLogin", false);
                        editor.putBoolean("rememberPass", false);
                        editor.apply();
                    }
                    Intent i = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btRegister.setOnClickListener(v -> {
            Intent j = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(j);
            finish();
        });
    }
}

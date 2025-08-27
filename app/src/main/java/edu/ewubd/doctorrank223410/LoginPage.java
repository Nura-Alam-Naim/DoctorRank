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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // ✅ If already logged in, go straight to main
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        sp = getSharedPreferences("login", MODE_PRIVATE);

        boolean rememberLogin = sp.getBoolean("rememberLogin", false);
        if (rememberLogin) {
            String email = sp.getString("email", "");
            String password = sp.getString("password", "");
            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginPage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPage.this, MainActivity.class));
                        finish();
                    }
                });
                return;
            }
        }

        // Show login UI
        setContentView(R.layout.activity_login_page);

        etEmail = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberLogin = findViewById(R.id.cbRememberLogin);
        cbRememberPass = findViewById(R.id.cbRememberPass);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);

        boolean rememberPass = sp.getBoolean("rememberPass", false);
        if (rememberPass) {
            etEmail.setText(sp.getString("email", ""));
            etPassword.setText(sp.getString("password", ""));
        }

        // ✅ Fix: fetch input only when login button is clicked
        btLogin.setOnClickListener(v -> {
            String enteredEmail = etEmail.getText().toString().trim();
            String enteredPassword = etPassword.getText().toString().trim();

            if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(LoginPage.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(task -> {
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
                        editor.remove("email");
                        editor.remove("password");
                        editor.apply();
                    }

                    startActivity(new Intent(LoginPage.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginPage.this, "Invalid Credentials: " +
                            (task.getException() != null ? task.getException().getMessage() : ""), Toast.LENGTH_LONG).show();
                }
            });
        });

        btRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginPage.this, RegisterPage.class));
            finish();
        });
    }
}

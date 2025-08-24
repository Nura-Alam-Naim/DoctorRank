package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etRePassword;
    private CheckBox cbMale, cbFemale;
    private Button btCreateAccount, btLogin;
    SharedPreferences sp,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);

        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);

        btCreateAccount = findViewById(R.id.btCreateAccount);
        btLogin = findViewById(R.id.btLogin);

        sp = getSharedPreferences("Login", MODE_PRIVATE); // SharedPreferences to store user data

        btCreateAccount.setOnClickListener(v -> {
            validateFields();
        });

        btLogin.setOnClickListener(v -> {
            // Navigate to Login page
            Intent i = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(i);
            finish();
        });
    }

    private void validateFields() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String rePass = etRePassword.getText().toString().trim();
        String phone=etPhone.getText().toString().trim();

        if (name.length() < 5) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmailAddress(email)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 4) {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(rePass)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbMale.isChecked() && !cbFemale.isChecked()) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name_" + email, name);  // Save name with unique key
        editor.putString("email_" + email, email);  // Save email with unique key
        editor.putString("phone_" + email, phone);  // Save phone with unique key
        editor.putString("password_" + email, pass);  // Save password with unique key
        editor.putString("gender_" + email, cbMale.isChecked()?"Male":"Female");
        editor.putString("isRememberLogin_"+email, "false");
        editor.putString("isRememberPass_"+email, "false");
        editor.apply();

        Toast.makeText(RegisterPage.this, "Account created successfully", Toast.LENGTH_SHORT).show();
        log=this.getSharedPreferences("my_pr", MODE_PRIVATE);
        SharedPreferences.Editor ed = log.edit();
        ed.putBoolean("isLogin", true);
        ed.apply();
        finish();
        return;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}

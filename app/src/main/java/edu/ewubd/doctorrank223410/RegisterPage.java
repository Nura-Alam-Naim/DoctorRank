package edu.ewubd.doctorrank223410;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etRePassword, etdob, etHeight, etWeight;
    private ImageView Userimg;
    private RadioButton cbMale, cbFemale;
    private Button btCreateAccount, btLogin;
    private String base64String;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase FDB;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        etdob=findViewById(R.id.etDOB);
        etHeight=findViewById(R.id.etHeight);
        etWeight=findViewById(R.id.etWeight);

        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);

        Userimg = findViewById(R.id.Userimg);

        btCreateAccount = findViewById(R.id.btCreateAccount);
        btLogin = findViewById(R.id.btLogin);

        Userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();

            }
        });
        btCreateAccount.setOnClickListener(v -> {
            validateFields();
        });

        btLogin.setOnClickListener(v -> {
            Intent i = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(i);
            finish();
        });
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Userimg.setImageURI(imageUri);
            base64String = convertImageToBase64(imageUri);
        }
    }

    private String convertImageToBase64(Uri imageUri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(imageUri);

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            assert inputStream != null;
            inputStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void validateFields() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String rePass = etRePassword.getText().toString().trim();
        String phone=etPhone.getText().toString().trim();
        String dob=etdob.getText().toString().trim();
        String height=etHeight.getText().toString().trim();
        String weight=etWeight.getText().toString().trim();
        String image=base64String;

        if (name.length() < 4) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmailAddress(email)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 6) {
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

        if (!isValidDOB(dob)) {
            Toast.makeText(this, "Invalid Date of Birth. Use correct format: DD/MM/YYYY", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isValidNumber(phone)){
            Toast.makeText(this,"Phone number is invalid",Toast.LENGTH_SHORT).show();
            return;
        }


        String gender= cbMale.isChecked() ? "Male" : "Female";

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("RegisterPage", "onComplete called. success? " + task.isSuccessful());

                        if (task.isSuccessful()) {
                            try {
                                // Safer way: always get current user from FirebaseAuth
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (firebaseUser == null) {
                                    Log.e("RegisterPage", "firebaseUser is NULL");
                                    Toast.makeText(RegisterPage.this, "FirebaseUser is null", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                String userId = firebaseUser.getUid();
                                Log.d("RegisterPage", "Got UID: " + userId);

                                Toast.makeText(RegisterPage.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                                // Get database reference
                                FDB = FirebaseDatabase.getInstance();
                                ref = FDB.getReference("users");
                                Log.d("RegisterPage", "DB ref path: " + ref.toString());

                                // Build user object
                                Log.d("RegisterPage", "Creating T_Users object...");
                                T_Users user = new T_Users(
                                        name,
                                        email,
                                        phone,
                                        pass,
                                        gender,
                                        dob,
                                        height,
                                        weight,
                                        image,
                                        userId
                                );
                                Log.d("RegisterPage", "T_Users object created: " + user.name);

                                // Save to Firebase
                                ref.child(userId).setValue(user)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d("RegisterPage", "User saved in DB successfully");
                                            Toast.makeText(RegisterPage.this, "User saved in DB", Toast.LENGTH_SHORT).show();
                                            // Navigate only after DB write success
                                            Intent i = new Intent(RegisterPage.this, LoginPage.class);
                                            startActivity(i);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("RegisterPage", "DB write failed", e);
                                            Toast.makeText(RegisterPage.this, "DB write failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        });

                            } catch (Exception e) {
                                Log.e("RegisterPage", "Exception in success block", e);
                                Toast.makeText(RegisterPage.this, "Crash in success block: " + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Exception e = task.getException();
                            Log.e("RegisterPage", "Auth failed", e);
                            Toast.makeText(RegisterPage.this, "Auth failed: " + (e != null ? e.getMessage() : "unknown"), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        finish();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidNumber(String number) {
        String nameRegex = "^01[356789]\\d{8}$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    public boolean isValidDOB(String dob)
    {
        String ePattern = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(dob);
        return m.matches();
    }
}

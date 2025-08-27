package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfilePage extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvName, tvDOB, tvHeight, tvWeight, tvPhone, tvEmail, tvGender;
    private CheckBox cbMale, cbFemale;
    private Button btLogout, btEditProfile, btAppointment;
    private String base64String;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);

        tvName = findViewById(R.id.tvName);
        tvDOB = findViewById(R.id.tvAge);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);

        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);

        btLogout = findViewById(R.id.btLogout);
        btEditProfile = findViewById(R.id.btEditProfile);
        btAppointment = findViewById(R.id.btAppointment);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            // ✅ make sure this matches your RegisterPage write path
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);

            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    T_Users user = task.getResult().getValue(T_Users.class);
                    if (user != null) {
                        tvName.setText(user.name);
                        tvEmail.setText(user.email);
                        tvPhone.setText(user.phone);
                        tvDOB.setText(user.dob);
                        tvHeight.setText(user.height);
                        tvWeight.setText(user.weight);
                        tvGender.setText(user.gender);

                        if (user.image != null && !user.image.isEmpty()) {
                            try {
                                byte[] bytes = Base64.decode(user.image, Base64.DEFAULT);
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                ivProfilePicture.setImageBitmap(bmp);
                                base64String = user.image;
                            } catch (Exception e) {
                                Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(this, "User record is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Failed to fetch user: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("rememberLogin", false);
                editor.apply();
                Toast.makeText(UserProfilePage.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserProfilePage.this, MainActivity.class));
                finish();
            }
        });
    }
}

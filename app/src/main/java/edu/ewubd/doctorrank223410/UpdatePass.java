package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePass extends AppCompatActivity {

    private EditText etPassword, etConfirmPassword, etCurPassword;
    private Button btBack, btUpdate;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        mAuth = FirebaseAuth.getInstance();

        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etCurPassword = findViewById(R.id.etCurPassword);
        btBack = findViewById(R.id.btBack);
        btUpdate = findViewById(R.id.btUpdate);

        btBack.setOnClickListener(v -> finish());

        btUpdate.setOnClickListener(v -> check());
    }

    private void check() {
        String newPassword = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String curPassword = etCurPassword.getText().toString().trim();

        if (newPassword.length() < 6) {
            Toast.makeText(UpdatePass.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(UpdatePass.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (curPassword.isEmpty()) {
            Toast.makeText(UpdatePass.this, "Enter current password", Toast.LENGTH_SHORT).show();
            return;
        }
        updatePassword(curPassword, newPassword);
    }

    private void updatePassword(String currentPassword, String newPassword) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            // redirect to user profile
                            Intent intent = new Intent(UpdatePass.this, UserProfilePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Update failed: " + updateTask.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

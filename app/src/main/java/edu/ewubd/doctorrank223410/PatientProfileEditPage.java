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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PatientProfileEditPage extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private EditText etName, etAge, etHeight, etWeight, etPhone;
    private RadioButton cbMale, cbFemale;
    private Button btBack, btHome, btSave;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String base64String;
    private String iphone, iheight, iweight, iname, igender, idob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit_page);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etPhone = findViewById(R.id.etPhone);

        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);

        btBack = findViewById(R.id.btBack);
        btHome = findViewById(R.id.btHome);
        btSave = findViewById(R.id.btSave);

        Intent intent = getIntent();
        if (intent != null) {
            SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
            String base64Image = sp.getString("ProfilePic", "");
            base64String = base64Image;
            if (base64Image != null && !base64Image.isEmpty()) {
                try {
                    byte[] bytes = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ivProfilePicture.setImageBitmap(bmp);
                } catch (Exception e) {
                    ivProfilePicture.setImageResource(R.drawable.dummy);
                }
            }
            iname = intent.getStringExtra("name");
            iphone = intent.getStringExtra("phone");
            idob = intent.getStringExtra("dob");
            iheight = intent.getStringExtra("height");
            iweight = intent.getStringExtra("weight");
            igender = intent.getStringExtra("gender");

            if (iname != null) etName.setText(iname);
            if (idob != null) etAge.setText(idob);
            if (iheight != null) etHeight.setText(iheight);
            if (iweight != null) etWeight.setText(iweight);
            if (iphone != null) etPhone.setText(iphone);

            if ("Male".equalsIgnoreCase(igender)) {
                cbMale.setChecked(true);
                cbFemale.setChecked(false);
            } else if ("Female".equalsIgnoreCase(igender)) {
                cbFemale.setChecked(true);
                cbMale.setChecked(false);
            }
        }

        ivProfilePicture.setOnClickListener(v -> openImageChooser());

        btBack.setOnClickListener(v -> finish());

        btHome.setOnClickListener(v -> {
            Intent i = new Intent(PatientProfileEditPage.this, UpdatePass.class);
            startActivity(i);
            finish();
        });

        btSave.setOnClickListener(v -> validateFields());
    }

    private void validateFields() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etAge.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String gender = cbMale.isChecked() ? "Male" : (cbFemale.isChecked() ? "Female" : "");

        if (name.length() < 4) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidDOB(dob)) {
            Toast.makeText(this, "Invalid Date of Birth. Use correct format: DD/MM/YYYY", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty()) phone = iphone;
        if (height.isEmpty()) height = iheight;
        if (weight.isEmpty()) weight = iweight;

        saveProfile(name, phone, dob, height, weight, gender);
    }

    public boolean isValidDOB(String dob) {
        String ePattern = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";
        return dob != null && dob.matches(ePattern);
    }

    private void saveProfile(String name, String phone, String dob, String height,
                             String weight, String gender) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = currentUser.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid);

        T_Users updatedUser = new T_Users(
                name,
                currentUser.getEmail(),
                phone,
                "Updated",
                gender,
                dob,
                height,
                weight,
                base64String,
                uid
        );

        ref.setValue(updatedUser).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PatientProfileEditPage.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(PatientProfileEditPage.this, "Update failed: " + task.getException().getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivProfilePicture.setImageURI(imageUri);
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
}

package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvDate, tvDay, tvNoAppointments, tvLog;
    private ListView lvDoctorList;
    private Button btProfile, btUpcomingAppointments, btTodayAppointment, btLogout;
    private SharedPreferences sp;

    private SearchView searchView;
    DoctorListAdapter adapter;
    private ArrayList<T_DoctorInfo> doctorInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvDate = findViewById(R.id.tvDate);
        tvDay = findViewById(R.id.tvDay);
        lvDoctorList = findViewById(R.id.lvDoctorList);
        btProfile = findViewById(R.id.btProfile);
        btUpcomingAppointments = findViewById(R.id.btUpcomingAppointments);
        btTodayAppointment = findViewById(R.id.btTodayAppointment);
        btLogout =findViewById(R.id.btLogout);
        searchView = findViewById(R.id.doctorSearchView);

        setDateAndDay();

        DoctorsDB db = DoctorsDB.get(this);
        doctorInfoList = db.GetAll();
        adapter = new DoctorListAdapter(this, doctorInfoList);
        lvDoctorList.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getSharedPreferences("my_pr", MODE_PRIVATE).edit().putBoolean("rememberLogin", false).apply();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finishAffinity();
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            btProfile.setText("Profile");
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUser.getUid());
            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    T_Users user = task.getResult().getValue(T_Users.class);
                    if (user != null && user.image != null && !user.image.isEmpty()) {
                        try {
                            byte[] bytes = Base64.decode(user.image, Base64.DEFAULT);
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ivProfilePicture.setImageBitmap(bmp);
                        } catch (Exception e) {
                            ivProfilePicture.setImageResource(R.drawable.dummy);
                        }
                    } else {
                        ivProfilePicture.setImageResource(R.drawable.dummy);
                    }
                }
            });
            btLogout.setVisibility(View.VISIBLE);
        } else {
            btProfile.setText("Login");
            btLogout.setVisibility(View.INVISIBLE);
        }

        btProfile.setOnClickListener(v -> {
            if (currentUser != null) {
                startActivity(new Intent(MainActivity.this, UserProfilePage.class));
            } else {
                startActivity(new Intent(MainActivity.this, LoginPage.class));
            }
        });

        btUpcomingAppointments.setOnClickListener(v -> {
            if (currentUser != null) {
                Intent i = new Intent(MainActivity.this, UpcomingAppointment.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginPage.class));
            }
        });

        btTodayAppointment.setOnClickListener(v -> {
            if (currentUser != null) {
                Intent i = new Intent(MainActivity.this, TodaysAppointment.class);
                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginPage.class));
            }

        });
    }

    void setDateAndDay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String currentDay = dayFormat.format(calendar.getTime());
        tvDate.setText(currentDate);
        tvDay.setText(currentDay);
    }
}

package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProfilePicture ;
    private TextView tvDate, tvDay,  tvNoAppointments, tvLog;
    private ListView lvDoctorList ;
    private DoctorListAdapter DocList;
    private LinearLayout SetVisible;
    private Button btProfile, btUpcomingAppointments, btBookPreferedDoc;
    private SharedPreferences sp;
    private AppointmentListAdapter todayAdapter;
    private ArrayList<UserBooking> todayAppointments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivProfilePicture = findViewById(R.id.ivProfilePicture) ;
        tvDate = findViewById(R.id.tvDate) ;
        tvDay = findViewById(R.id.tvDay) ;
        lvDoctorList = findViewById(R.id.lvDoctorList) ;
        btProfile = findViewById(R.id.btProfile) ;
        btUpcomingAppointments = findViewById(R.id.btUpcomingAppointments) ;
        btBookPreferedDoc = findViewById(R.id.btBookPreferedDoc) ;
        tvNoAppointments = findViewById(R.id.tvNoAppointments);
        tvLog=findViewById(R.id.tvLog);
        SetVisible = findViewById(R.id.SetVisible);
        setDateAndDay();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null)
        {
            tvLog.setVisibility(View.GONE);
            SetVisible.setVisibility(View.VISIBLE);
            todayAdapter = new AppointmentListAdapter(this, todayAppointments);
            lvDoctorList.setAdapter(todayAdapter);
            loadTodaysAppointments(currentUser.getUid());
        }
        else
        {
            tvLog.setVisibility(View.VISIBLE);
            SetVisible.setVisibility(View.GONE);
        }
        if (currentUser != null) {
            btProfile.setText("Profile");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    T_Users user = task.getResult().getValue(T_Users.class);
                    if (user != null) {
                        if (user.image != null && !user.image.isEmpty()) {
                            try {
                                byte[] bytes = Base64.decode(user.image, Base64.DEFAULT);
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                ivProfilePicture.setImageBitmap(bmp);
                            } catch (Exception e) {
                                ivProfilePicture.setImageResource(R.drawable.dummy);
                            }
                        }
                    }
                    else
                    {
                        ivProfilePicture.setImageResource(R.drawable.dummy);
                    }
                }
            });
        } else {
            btProfile.setText("Login");
        }
        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null)
                {
                    Intent i=new Intent(MainActivity.this, UserProfilePage.class);
                    startActivity(i);
                }
                else
                {
                    Intent i=new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);
                    recreate();
                }
            }
        });
        btUpcomingAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null)
                {
                    Intent i=new Intent(MainActivity.this, UpcomingAppointment.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);
                }
            }
        });
        btBookPreferedDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, DoctorSelectionPage.class);
                startActivity(i);
            }
        });
    }
    void setDateAndDay()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String currentDay = dayFormat.format(calendar.getTime());
        tvDate.setText(currentDate);
        tvDay.setText(currentDay);
    }
    private void loadTodaysAppointments(String userId) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("user_bookings")
                .child(userId);

        ref.get().addOnSuccessListener(snapshot -> {
            ArrayList<UserBooking> localList = new ArrayList<>();

            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.set(java.util.Calendar.SECOND, 0);
            cal.set(java.util.Calendar.MILLISECOND, 0);
            Date today = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            for (DataSnapshot child : snapshot.getChildren()) {
                UserBooking booking = child.getValue(UserBooking.class);
                if (booking != null) {
                    try {
                        Date bookingDate = sdf.parse(booking.date);
                        if (bookingDate != null && bookingDate.equals(today)) {
                            localList.add(booking);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            todayAppointments.clear();
            todayAppointments.addAll(localList);
            todayAdapter.notifyDataSetChanged();

            if (todayAppointments.isEmpty()) {
                tvNoAppointments.setVisibility(View.VISIBLE);
                lvDoctorList.setVisibility(View.GONE);
            } else {
                tvNoAppointments.setVisibility(View.GONE);
                lvDoctorList.setVisibility(View.VISIBLE);
            }

        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to load today's appointments: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

}
package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DoctorSelectionPage extends AppCompatActivity {
    private ListView lvDoctorSelection;
    private SearchView searchView;

    private AppointmentListAdapter todayAdapter;
    private ArrayList<UserBooking> todayAppointments = new ArrayList<>();
    private TextView tvNoAppointments;
    DoctorListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_selection_page);
        lvDoctorSelection = findViewById(R.id.lvDoctorList);
        tvNoAppointments = findViewById(R.id.tvNoAppointments);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        todayAdapter = new AppointmentListAdapter(this, todayAppointments);
        lvDoctorSelection.setAdapter(todayAdapter);
        loadTodaysAppointments(currentUser.getUid());

    }
    private void loadTodaysAppointments(String userId) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("user_bookings")
                .child(userId);

        ref.get().addOnSuccessListener(snapshot -> {
            ArrayList<UserBooking> localList = new ArrayList<>();

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
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
            if (todayAdapter != null) todayAdapter.notifyDataSetChanged();

            if (todayAppointments.isEmpty()) {
                tvNoAppointments.setVisibility(View.VISIBLE);
                lvDoctorSelection.setVisibility(View.GONE);
            } else {
                tvNoAppointments.setVisibility(View.GONE);
                lvDoctorSelection.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this,
                        "Failed to load today's appointments: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show()
        );
    }
}
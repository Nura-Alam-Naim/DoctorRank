package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodaysAppointment extends AppCompatActivity {
    private ListView lvDoctorSelection;
    private SearchView searchView;

    private AppointmentListAdapter todayAdapter;
    private ArrayList<UserBooking> todayAppointments = new ArrayList<>();
    private TextView tvNoAppointments;
    private Button btBack;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    DoctorListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todaysappointsment);
        lvDoctorSelection = findViewById(R.id.lvDoctorList);
        tvNoAppointments = findViewById(R.id.tvNoAppointments);
        btBack = findViewById(R.id.btBack);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        todayAdapter = new AppointmentListAdapter(this, todayAppointments);
        lvDoctorSelection.setAdapter(todayAdapter);
        loadTodaysAppointments(currentUser.getUid());

        btBack.setOnClickListener(v -> finish());

    }
    private void loadTodaysAppointments(String userId) {
        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance()
                .getReference("user_bookings")
                .child(userId);

        ref.get()
                .addOnSuccessListener(snapshot -> {
                    executor.execute(() -> {
                        ArrayList<UserBooking> localList = new ArrayList<>();

                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                        java.util.Calendar cal = java.util.Calendar.getInstance();
                        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                        cal.set(java.util.Calendar.MINUTE, 0);
                        cal.set(java.util.Calendar.SECOND, 0);
                        cal.set(java.util.Calendar.MILLISECOND, 0);
                        String todayStr = sdf.format(cal.getTime());

                        for (com.google.firebase.database.DataSnapshot child : snapshot.getChildren()) {
                            UserBooking booking = child.getValue(UserBooking.class);
                            if (booking != null && booking.date != null && todayStr.equals(booking.date)) {
                                localList.add(booking);
                            }
                        }

                        handler.post(() -> {
                            todayAppointments.clear();
                            todayAppointments.addAll(localList);
                            todayAdapter.notifyDataSetChanged();

                            if (todayAppointments.isEmpty()) {
                                tvNoAppointments.setVisibility(View.VISIBLE);
                                lvDoctorSelection.setVisibility(View.GONE);
                            } else {
                                tvNoAppointments.setVisibility(View.GONE);
                                lvDoctorSelection.setVisibility(View.VISIBLE);
                            }
                        });
                    });
                })
                .addOnFailureListener(e ->
                        handler.post(() ->
                                android.widget.Toast.makeText(this, "Failed to load today's appointments: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show()
                        )
                );
    }
}
package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpcomingAppointment extends AppCompatActivity {

    private ListView lvUpComingAppointments;
    private Button btBack;
    private AppointmentListAdapter adapter;
    private ArrayList<UserBooking> upcomingAppointments = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appoinment);

        lvUpComingAppointments = findViewById(R.id.lvUpComingAppointments);
        btBack = findViewById(R.id.btBack);

        adapter = new AppointmentListAdapter(this, upcomingAppointments);
        lvUpComingAppointments.setAdapter(adapter);

        btBack.setOnClickListener(v -> finish());

        String userId = FirebaseAuth.getInstance().getUid();
        if (userId != null) {
            loadUpcomingAppointments(userId);
        } else {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadUpcomingAppointments(String userId) {
        executor.execute(() -> {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("user_bookings")
                    .child(userId);

            ref.get().addOnSuccessListener(snapshot -> {
                ArrayList<UserBooking> localList = new ArrayList<>();

                Date now = new Date();

                for (DataSnapshot child : snapshot.getChildren()) {
                    UserBooking booking = child.getValue(UserBooking.class);
                    if (booking != null) {
                        try {
                            Date bookingDate = sdf.parse(booking.date);
                            if (bookingDate != null && bookingDate.after(now)) {
                                localList.add(booking);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                handler.post(() -> {
                    upcomingAppointments.clear();
                    upcomingAppointments.addAll(localList);
                    adapter.notifyDataSetChanged();
                });

            }).addOnFailureListener(e ->
                    handler.post(() ->
                            Toast.makeText(UpcomingAppointment.this, "Failed to load data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    )
            );
        });
    }
}

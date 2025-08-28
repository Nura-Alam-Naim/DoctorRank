package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAppointments extends AppCompatActivity {

    private ListView lvAppointments;
    private AppointmentListAdapter adapter;
    private ArrayList<Booking> appointmentList = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        lvAppointments = findViewById(R.id.lvAppointments);
        String userId = FirebaseAuth.getInstance().getUid();
        loadAppointments(userId);
        adapter = new AppointmentListAdapter(this, appointmentList);
        lvAppointments.setAdapter(adapter);
    }
    private void loadAppointments(String userId) {
        executor.execute(() -> {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("user_bookings")
                    .child(userId);

            ref.get().addOnSuccessListener(snapshot -> {
                ArrayList<Booking> localList = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Booking booking = child.getValue(Booking.class);
                    if (booking != null) {
                        localList.add(booking);
                    }
                }
                handler.post(() -> {
                    appointmentList.clear();
                    appointmentList.addAll(localList);
                });
            });
        });
    }
}

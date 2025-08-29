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
    private ArrayList<UserBooking> appointmentList = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) {
            Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
            return;
        }
        setContentView(R.layout.activity_my_appointments);

        lvAppointments = findViewById(R.id.lvAppointments);
        adapter = new AppointmentListAdapter(this, appointmentList);
        lvAppointments.setAdapter(adapter);

        loadAppointments(userId);
    }

    private void loadAppointments(String userId) {
        executor.execute(() -> {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("user_bookings")
                    .child(userId);

            // still async, but runs inside our executor thread
            ref.get().addOnSuccessListener(snapshot -> {
                ArrayList<UserBooking> localList = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    UserBooking booking = child.getValue(UserBooking.class);
                    if (booking != null) {
                        localList.add(booking);
                        System.out.println("Adding info");
                    }
                }

                // push result back to UI thread
                handler.post(() -> {
                    appointmentList.clear();
                    appointmentList.addAll(localList);
                    adapter.notifyDataSetChanged();
                });

            }).addOnFailureListener(e ->
                    handler.post(() -> Toast.makeText(
                            MyAppointments.this,
                            "Failed to load data: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show()
                    )
            );
        });
    }
}

package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.CalendarView;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateSelection extends AppCompatActivity {

    private CalendarView calendar;
    private ListView lvTimeSchedule;
    private Button btBack, btConfirm;

    private String doctorId, doctorName;
    private String selectedDate;
    private ArrayList<String> slots = new ArrayList<>();
    private TimeListAdapter adapter;
    private T_DoctorInfo doctor;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        Intent i = getIntent();
        if (i != null) {
            doctorId = i.getStringExtra("doctorId");
            doctorName = i.getStringExtra("doctorName");
        }

        calendar = findViewById(R.id.calendar);
        calendar.setMinDate(System.currentTimeMillis() - 1000);
        lvTimeSchedule = findViewById(R.id.lvTimeSchedule);
        btBack = findViewById(R.id.btBack);
        btConfirm = findViewById(R.id.btConfirm);

        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadSlotsForDate(selectedDate);

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            try {
                Date parsed = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate);
                String dayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(parsed);

                if (doctor != null && doctor.schedule != null && doctor.schedule.containsKey(dayName)) {
                    loadSlotsForDate(selectedDate);
                } else {
                    slots.clear();
                    if (adapter != null) adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Doctor is not available on " + dayName, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btBack.setOnClickListener(v -> finish());

        btConfirm.setOnClickListener(v -> {
            if (adapter == null) return;

            String chosenSlot = adapter.getSelectedSlot();
            if (chosenSlot == null) {
                Toast.makeText(this, "Please select a slot!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = FirebaseAuth.getInstance().getUid();
            Booking booking = new Booking(userId, doctorId, doctorName,
                    doctor.speciality, doctor.roomNo, selectedDate, chosenSlot);

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("bookings")
                    .child(doctorId)
                    .child(selectedDate)
                    .child(chosenSlot);

            ref.setValue(booking).addOnSuccessListener(aVoid -> {
                Intent intent = new Intent(DateSelection.this, confirmation.class);
                intent.putExtra("appointmentDate", selectedDate);
                intent.putExtra("appointmentTime", chosenSlot);
                intent.putExtra("doctorName", doctor.name);
                intent.putExtra("specialization", doctor.speciality);
                intent.putExtra("roomNo", String.valueOf(doctor.roomNo));
                startActivity(intent);
                finish();
            });
        });
    }

    private void loadSlotsForDate(String date) {
        executor.execute(() -> {
            doctor = DoctorsDB.get(this).getById(doctorId);
            if (doctor == null) {
                handler.post(() -> Toast.makeText(this, "Doctor not found!", Toast.LENGTH_SHORT).show());
                return;
            }

            String dayName = null;
            try {
                Date parsed = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date);
                dayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(parsed);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ArrayList<String> localSlots = new ArrayList<>();
            if (dayName != null && doctor.schedule != null && doctor.schedule.containsKey(dayName)) {
                List<String> daySlots = doctor.schedule.get(dayName);
                if (daySlots != null) localSlots.addAll(daySlots);
            }

            String finalDayName = dayName;
            handler.post(() -> {
                slots.clear();
                slots.addAll(localSlots);

                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference("bookings")
                        .child(doctorId)
                        .child(date);

                ref.get().addOnSuccessListener(snapshot -> {
                    ArrayList<String> bookedSlots = new ArrayList<>();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        bookedSlots.add(child.getKey());
                    }

                    adapter = new TimeListAdapter(this, slots, bookedSlots);
                    lvTimeSchedule.setAdapter(adapter);

                    if (slots.isEmpty()) {
                        Toast.makeText(this, "Doctor not available on " + finalDayName, Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}

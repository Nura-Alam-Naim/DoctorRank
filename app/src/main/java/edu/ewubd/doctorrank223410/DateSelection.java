package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateSelection extends AppCompatActivity {

    private CalendarView calendar;
    private ListView lvTimeSchedule;
    private Button btBack, btConfirm;

    private String doctorId, doctorName;
    private String selectedDate;
    private ArrayList<String> slots = new ArrayList<>();
    private TimeListAdapter adapter;
    private T_DoctorInfo doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        // --- Get doctor info from intent ---
        Intent i = getIntent();
        if (i != null) {
            doctorId = i.getStringExtra("doctorId");
            doctorName = i.getStringExtra("doctorName");
        }

        // --- Init views ---
        calendar = findViewById(R.id.calendar);
        lvTimeSchedule = findViewById(R.id.lvTimeSchedule);
        btBack = findViewById(R.id.btBack);
        btConfirm = findViewById(R.id.btConfirm);

        // Default → today
        selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        loadSlotsForDate(selectedDate);

        // Listen to calendar changes
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);

            // --- Figure out the day name ---
            try {
                String dateStr = selectedDate;
                Date parsed = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr);
                String dayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(parsed);

                // --- Check if doctor has slots on this day ---
                if (doctor != null && doctor.schedule != null && doctor.schedule.containsKey(dayName)) {
                    loadSlotsForDate(selectedDate);   // ✅ valid day, load slots
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

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("bookings")
                    .child(doctorId)
                    .child(selectedDate)
                    .child(chosenSlot);

            String userId = FirebaseAuth.getInstance().getUid();

            Booking booking = new Booking(userId, doctorId, doctorName, doctor.speciality,
                    doctor.roomNo, selectedDate, chosenSlot);
            ref.setValue(booking);
            Intent intent = new Intent(DateSelection.this, confirmation.class);
            intent.putExtra("appointmentDate", selectedDate);
            intent.putExtra("appointmentTime", chosenSlot);
            intent.putExtra("doctorName", doctor.name);
            intent.putExtra("specialization", doctor.speciality);
            intent.putExtra("roomNo", doctor.roomNo);
            finish();
        });
    }

    private void loadSlotsForDate(String date) {
        doctor = DoctorsDB.get(this).getById(doctorId);
        if (doctor == null) {
            Toast.makeText(this, "Doctor not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- Figure out which weekday this date is ---
        String dayName = new SimpleDateFormat("EEEE", Locale.getDefault())
                .format(new Date(date.replace("-", "/")));  // ensure proper parsing

        slots.clear();
        if (doctor.schedule != null && doctor.schedule.containsKey(dayName)) {
            List<String> daySlots = doctor.schedule.get(dayName);
            if (daySlots != null) slots.addAll(daySlots);
        }

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(doctorId)
                .child(date);

        ref.get().addOnSuccessListener(snapshot -> {
            ArrayList<String> bookedSlots = new ArrayList<>();
            for (DataSnapshot child : snapshot.getChildren()) {
                Boolean booked = child.getValue(Boolean.class);
                if (booked != null && booked) {
                    bookedSlots.add(child.getKey());
                }
            }

            adapter = new TimeListAdapter(this, slots, bookedSlots);
            lvTimeSchedule.setAdapter(adapter);
        });
    }
}

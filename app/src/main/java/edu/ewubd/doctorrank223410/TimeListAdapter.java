package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TimeListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> slots;        // All slots for the day
    private final ArrayList<String> bookedSlots;  // Already booked slots
    private String selectedSlot = null;           // Track user selection

    public TimeListAdapter(@NonNull Context context,
                           @NonNull ArrayList<String> slots,
                           @NonNull ArrayList<String> bookedSlots) {
        super(context, 0, slots);
        this.context = context;
        this.slots = slots;
        this.bookedSlots = bookedSlots;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.time_selection, parent, false);
        }

        Button btnSlot = convertView.findViewById(R.id.btnSlot);
        String slot = slots.get(position);

        btnSlot.setText(slot);

        // Disable button if booked
        if (bookedSlots.contains(slot)) {
            btnSlot.setEnabled(false);
            btnSlot.setBackgroundColor(0xFFAAAAAA); // gray for booked
        } else {
            btnSlot.setEnabled(true);
            btnSlot.setBackgroundColor(0xFF06A9B1); // teal for available
        }

        // Highlight selected slot
        if (slot.equals(selectedSlot)) {
            btnSlot.setBackgroundColor(0xFFF29E23); // orange highlight
        }

        // Handle click
        btnSlot.setOnClickListener(v -> {
            selectedSlot = slot;
            notifyDataSetChanged(); // refresh UI so only one button looks selected
        });

        return convertView;
    }

    public String getSelectedSlot() {
        return selectedSlot;
    }
}

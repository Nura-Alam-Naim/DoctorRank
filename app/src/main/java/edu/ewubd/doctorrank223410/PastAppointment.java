package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PastAppointment extends AppCompatActivity {

    private ListView lvPastAppointments ;
    private Button btBack ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_appoinment);

        lvPastAppointments = findViewById(R.id.lvPastAppointments) ;

        btBack = findViewById(R.id.btBack) ;

    }
}
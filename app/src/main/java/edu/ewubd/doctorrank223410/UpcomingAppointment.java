package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpcomingAppointment extends AppCompatActivity {

    private ListView lvUpComingAppointments ;
    private Button btBack ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_appoinment);

        lvUpComingAppointments = findViewById(R.id.lvUpComingAppointments) ;

        btBack = findViewById(R.id.btBack) ;

    }
}
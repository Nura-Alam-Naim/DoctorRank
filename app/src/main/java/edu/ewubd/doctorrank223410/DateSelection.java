package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DateSelection extends AppCompatActivity {

    private CalendarView calendar ;
    private ListView lvTimeSchedule;
    private Button btBack, btConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        calendar = findViewById(R.id.calendar) ;

        lvTimeSchedule = findViewById(R.id.lvTimeSchedule) ;

        btBack = findViewById(R.id.btBack) ;
        btConfirm = findViewById(R.id.btConfirm) ;
    }
}
package edu.ewubd.doctorrank223410;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProfilePicture ;
    private TextView tvDate, tvDay ;
    private ListView lvDoctorList ;
    private Button btProfile, btUpcomingAppointments, btBookPreferedDoc, btMyAppoinments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivProfilePicture = findViewById(R.id.ivProfilePicture) ;

        tvDate = findViewById(R.id.tvDate) ;
        tvDay = findViewById(R.id.tvDay) ;

        lvDoctorList = findViewById(R.id.lvDoctorList) ;

        btProfile = findViewById(R.id.btProfile) ;
        btUpcomingAppointments = findViewById(R.id.btUpcomingAppointments) ;
        btBookPreferedDoc = findViewById(R.id.btBookPreferedDoc) ;
        btMyAppoinments = findViewById(R.id.btMyAppoinments) ;

    }
}
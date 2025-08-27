package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProfilePicture ;
    private TextView tvDate, tvDay ;
    private ListView lvDoctorList ;
    private DoctorListAdapter DocList;
    private Button btProfile, btUpcomingAppointments, btBookPreferedDoc, btMyAppoinments;
    private SharedPreferences sp;
    private ArrayList<T_DoctorInfo> DoctorInfo=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivProfilePicture = findViewById(R.id.ivProfilePicture) ;
        ivProfilePicture.setImageResource(R.drawable.dummy);
        tvDate = findViewById(R.id.tvDate) ;
        tvDay = findViewById(R.id.tvDay) ;
        lvDoctorList = findViewById(R.id.lvDoctorList) ;
        btProfile = findViewById(R.id.btProfile) ;
        btUpcomingAppointments = findViewById(R.id.btUpcomingAppointments) ;
        btBookPreferedDoc = findViewById(R.id.btBookPreferedDoc) ;
        btMyAppoinments = findViewById(R.id.btMyAppoinments) ;
        setDateAndDay();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            btProfile.setText("Profile");
        } else {
            btProfile.setText("Login");
        }


        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null)
                {
                    Intent i=new Intent(MainActivity.this, UserProfilePage.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);
                }
            }
        });
        btUpcomingAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null)
                {
                    Intent i=new Intent(MainActivity.this, UpcomingAppointment.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);
                }
            }
        });

        btMyAppoinments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser != null)
                {
                    Intent i=new Intent(MainActivity.this, PastAppointment.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);
                }
            }
        });

        btBookPreferedDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, DoctorSelectionPage.class);
                startActivity(i);
            }
        });

        DocList=new DoctorListAdapter(this,DoctorInfo);
        lvDoctorList.setAdapter(DocList);

    }
    void setDateAndDay()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String currentDay = dayFormat.format(calendar.getTime());
        tvDate.setText(currentDate);
        tvDay.setText(currentDay);
    }
}
package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.ArrayList;

public class DoctorSelectionPage extends AppCompatActivity {
    private ListView lvDoctorSelection;
    private SearchView searchView;
    private ArrayList<T_DoctorInfo> doctorInfoList=new ArrayList<T_DoctorInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_selection_page);

        System.out.println("On This");
        lvDoctorSelection = findViewById(R.id.lvDoctorSelection);
        searchView = findViewById(R.id.doctorSearchView);

        DoctorsDB db = DoctorsDB.get(this);
        checkDatabase(this);
        doctorInfoList=db.GetAll();
        System.out.println(doctorInfoList.size());
        DoctorListAdapter adapter=new DoctorListAdapter(this,doctorInfoList);
        lvDoctorSelection.setAdapter(adapter);

    }
    public void checkDatabase(Context context) {
        File dbFile = context.getDatabasePath("Doctors.db");
        if (dbFile.exists()) {
            Log.d("DB_CHECK", "Database exists at: " + dbFile.getAbsolutePath());
        } else {
            Log.d("DB_CHECK", "Database does NOT exist!");
        }
    }
}
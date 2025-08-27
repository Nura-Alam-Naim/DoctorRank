package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Welcome extends AppCompatActivity {

    private Button btGetStarted;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp=getSharedPreferences("my_pr", MODE_PRIVATE);
        boolean isDone=sp.getBoolean("isDone", false);
        boolean seeded = sp.getBoolean("seeded", false);
        if (!seeded) {
            DoctorSeedService.enqueue(this);
        }
        if(isDone)
        {
            Intent i=new Intent(Welcome.this, MainActivity.class);
            startActivity(i);
            finishAffinity();
            return;
        }
        setContentView(R.layout.activity_welcome);

        btGetStarted = findViewById(R.id.btGetStarted);
        //ivLogo.setImageResource(R.drawable.logo);
        btGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor prEdit = sp.edit();
                prEdit.putBoolean("isDone", true);
                prEdit.apply();
                Intent i = new Intent(Welcome.this, MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });



    }
}
package edu.ewubd.doctorrank223410;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log; // <-- added

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DoctorSeedService extends JobIntentService {
    public static final int JOB_ID = 2001;

    public static void enqueue(Welcome ctx) {
        enqueueWork(ctx, DoctorSeedService.class, JOB_ID,
                new Intent(ctx, DoctorSeedService.class));
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        try {
            Log.d("DoctorSeedService", "onHandleWork: started");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("doctors");
            DataSnapshot snap = Tasks.await(ref.get());

            Log.d("DoctorSeedService", "Firebase get() done. exists=" + snap.exists() +
                    " children=" + (snap.exists() ? snap.getChildrenCount() : 0));

            if (snap.exists()) {
                List<T_DoctorInfo> doctors = new ArrayList<>();
                for (DataSnapshot child : snap.getChildren()) {
                    T_DoctorInfo d = child.getValue(T_DoctorInfo.class);
                    if (d == null) {
                        Log.w("DoctorSeedService", "Null doctor at key=" + child.getKey());
                        continue;
                    }
                    if (d.id == null || d.id.isEmpty()) d.id = child.getKey();
                    if (d.picture != null && !d.picture.isEmpty()) {
                        Bitmap bmp = getBitmapFromURL(d.picture);
                        if (bmp != null) {
                            String base64 = bitmapToBase64(bmp);
                            d.picture = base64;
                        } else {
                            Log.w("DoctorSeedService", "Image fetch failed for: " + d.picture);
                        }
                    }
                    doctors.add(d);
                }
                DoctorsDB.get(getApplicationContext()).saveAll(doctors);
                getSharedPreferences("my_pr", MODE_PRIVATE).edit().putBoolean("seeded", true).apply();
            }

            Log.d("DoctorSeedService", "Seeding complete (or no data)."); // STEP 3 (log 3)
        } catch (Exception e) {
            Log.e("DoctorSeedService", "onHandleWork exception", e); // STEP 3 (error log)
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            Log.e("DoctorSeedService", "getBitmapFromURL failed for url=" + src, e);
            e.printStackTrace();
            return null;
        }
    }

    private String bitmapToBase64(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}

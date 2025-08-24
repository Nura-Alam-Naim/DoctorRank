package edu.ewubd.doctorrank223410;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class TimeListAdapter extends ArrayAdapter<T_DoctorInfo> {

    private final Context context;
    private final ArrayList<T_DoctorInfo> values;

    public TimeListAdapter(@NonNull Context context, @NonNull ArrayList<T_DoctorInfo> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.doctor_adapter_row, parent, false);

//        TextView tvSN = rowView.findViewById(R.id.tvSerial);
//        TextView tvName = rowView.findViewById(R.id.tvName);
//        EditText etRemark = rowView.findViewById(R.id.etRemark);
//        RadioButton rbAbsent=rowView.findViewById(R.id.rbAbsent);
//        RadioButton rbPresent=rowView.findViewById(R.id.rbPresent);
//        //TextView eventType = rowView.findViewById(R.id.tvEventType);

//        StudentAttendence sa = values.get(position);
//        tvSN.setText(""+(position+1));
//        tvName.setText(sa.name);
//        etRemark.setText(sa.remark);
//        rbPresent.setChecked(sa.status);
//        rbAbsent.setChecked(!sa.status);


//        rbPresent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                values.get(position).status=true;
//            }
//        });
//
//        rbAbsent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                values.get(position).status=false;
//            }
//        });

        return rowView;
    }
}

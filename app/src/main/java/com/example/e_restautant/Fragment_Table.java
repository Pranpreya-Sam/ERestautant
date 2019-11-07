package com.example.e_restautant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;
import java.util.Locale;

public class Fragment_Table extends Fragment {

    private EditText people;
    private TextView date;
    private Calendar calendar;
    private ImageButton imageButtonCal;
    private ImageGenerator imageGenerator;

    private TextView time;
    private TimePickerDialog timePicker;
        private ImageButton imageButtonTime;

    private String amount;
    private String specialRequest;
    private String dateT;
    private String timeT;

    private EditText special;

    private Button button;

    private  TextView thai;
    private  TextView english;

    private DatabaseHelperTable helperTable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View temp = inflater.inflate(R.layout.fragment_table, null);

        thai = temp.findViewById(R.id.thai4);
        english = temp.findViewById(R.id.english4);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(getId(), new Fragment_Table());
                ft.commit();

            }
        });


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("en");
                getResources().updateConfiguration(configuration, null);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(getId(), new Fragment_Table());
                ft.commit();

            }
        });


        //amount of people
        people = temp.findViewById(R.id.amount_people);

        //Special Request
        special = (EditText)temp.findViewById(R.id.special_request);

        //Time
        time = (TextView)temp.findViewById(R.id.arrive_time);
        imageButtonTime = (ImageButton) temp.findViewById(R.id.image_time);
        imageButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timePicker = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });


        //Date
        date = (TextView) temp.findViewById(R.id.arrive_date);
        imageButtonCal = (ImageButton) temp.findViewById(R.id.image_calendar);
        imageGenerator = new ImageGenerator(getContext());

        imageGenerator.setIconSize(50,50);
        imageGenerator.setDateSize(30);
        imageGenerator.setMonthSize(40);

        imageGenerator.setDatePosition(42);
        imageGenerator.setMonthPosition(14);

        imageGenerator.setDateColor(Color.YELLOW);
        imageGenerator.setMonthColor(Color.WHITE);

        imageGenerator.setStorageToSDCard(true);
        imageButtonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.valueOf(dayOfMonth)+'-'+String.valueOf(month+1)+'-'+String.valueOf(year));
                        calendar.set(year,month,dayOfMonth);
//                        dateT = date.getText().toString();
//                        Log.d("DateTest", dateT);

                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });

        button = temp.findViewById(R.id.button_table);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get amount
                amount = people.getText().toString();
                dateT = date.getText().toString();
                timeT = time.getText().toString();
                specialRequest = special.getText().toString();

                helperTable = new DatabaseHelperTable(getContext());
                helperTable.addTable(new Table(amount, dateT, timeT, specialRequest));
                Toast.makeText(getContext(),"Reserve Table Success!",Toast.LENGTH_LONG).show();
//                Log.d("AmountT",String.valueOf(amount));
//                Log.d("specialR", specialRequest);
//                Log.d("DateTest", dateT);
//                Log.d("TimeT",String.valueOf(timeT));
//                Log.d("TableAdd", String.valueOf( helperTable.addTable(new Table(amount, dateT, timeT, specialRequest))));
            }
        });


        return temp;
    }

}
package codewithcal.au.calendarappexample;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class NewEvenAcitivity extends AppCompatActivity {

    EditText addTaskTitle;
    EditText addTaskDescription;
    EditText taskDate;
    DatePickerDialog.OnDateSetListener setListener;
    EditText taskTime;
    private LocalTime time;
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_even_acitivity);
        initWidgets();
        time = LocalTime.now();

        //Task Time
        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(NewEvenAcitivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >=12 ){
                            amPm = "PM";
                        } else{
                            amPm ="AM";
                        }
                        taskTime.setText(String.format("%02d:%02d", hourOfDay, minutes)+amPm);
                        taskTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });
        //task Date
        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        taskDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewEvenAcitivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        taskDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }
    private void initWidgets()
    {
        addTaskTitle=findViewById(R.id.addTaskTitle);
        addTaskDescription=findViewById(R.id.addTaskDescription);
        taskDate = findViewById(R.id.taskDate);
        taskTime = findViewById(R.id.taskTime);
    }

    public void saveEventAction(View view)
    {
        String addTitle = addTaskTitle.getText().toString();
        String addDescription = addTaskDescription.getText().toString();
        Event newEvent = new Event(addTitle, addDescription, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }

}


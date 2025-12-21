package com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.interfaces.OnDateSelectedListener;
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.objects.CalendarDate;
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.views.CustomCalendarView;


public class Calender_Main extends AppCompatActivity implements OnDateSelectedListener {

    private TextView mTextDay;
    private TextView mTextDayOfWeek;
    private CustomCalendarView mCustomCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_main);

        mTextDay = findViewById(R.id.activity_main_text_day_of_month);
        mTextDayOfWeek = findViewById(R.id.activity_main_text_day_of_week);
        mCustomCalendar = findViewById(R.id.activity_main_view_custom_calendar);
        mCustomCalendar.setOnDateSelectedListener(this);
    }

    @Override
    public void onDateSelected(CalendarDate date) {
        mTextDay.setText(date.dayToString());
        mTextDayOfWeek.setText(date.dayOfWeekToStringName());
    }
}

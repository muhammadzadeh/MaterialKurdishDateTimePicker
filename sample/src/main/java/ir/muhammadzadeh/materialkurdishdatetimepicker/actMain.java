package ir.muhammadzadeh.materialkurdishdatetimepicker;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ir.muhammadzadeh.materialkurdishdatetimepicker.date.DatePickerDialog;
import ir.muhammadzadeh.materialkurdishdatetimepicker.multidate.MultiDatePickerDialog;
import ir.muhammadzadeh.materialkurdishdatetimepicker.time.RadialPickerLayout;
import ir.muhammadzadeh.materialkurdishdatetimepicker.time.TimePickerDialog;
import ir.muhammadzadeh.materialkurdishdatetimepicker.utils.KurdishCalendar;

public class actMain extends AppCompatActivity implements
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener,
    MultiDatePickerDialog.OnDateSetListener,
    View.OnClickListener {

        private static final String TIMEPICKER = "TimePickerDialog",
                DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";

        private CheckBox mode24Hours, modeDarkTime, modeDarkDate;
        private TextView timeTextView, dateTextView, multiDateTextView;
        private Button timeButton, dateButton, multiDataButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_main);
            initializeViews();
            handleClicks();
        }

    private void initializeViews() {
        timeTextView = (TextView) findViewById(R.id.time_textview);
        dateTextView = (TextView) findViewById(R.id.date_textview);
        multiDateTextView = (TextView) findViewById(R.id.multi_date_textview);
        timeButton = (Button) findViewById(R.id.time_button);
        dateButton = (Button) findViewById(R.id.date_button);
        multiDataButton = (Button) findViewById(R.id.multi_date_button);
        mode24Hours = (CheckBox) findViewById(R.id.mode_24_hours);
        modeDarkTime = (CheckBox) findViewById(R.id.mode_dark_time);
        modeDarkDate = (CheckBox) findViewById(R.id.mode_dark_date);
    }

    private void handleClicks() {
        timeButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        multiDataButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_button: {
                KurdishCalendar now = new KurdishCalendar();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        actMain.this,
                        now.get(KurdishCalendar.HOUR_OF_DAY),
                        now.get(KurdishCalendar.MINUTE),
                        mode24Hours.isChecked()
                );
                tpd.setThemeDark(modeDarkTime.isChecked());
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d(TIMEPICKER, "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), TIMEPICKER);
                break;
            }
            case R.id.date_button: {
                KurdishCalendar now = new KurdishCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        actMain.this,
                        now.getKurdishYear(),
                        now.getKurdishMonth(),
                        now.getKurdishDay()
                );
                dpd.setThemeDark(modeDarkDate.isChecked());
                dpd.show(getFragmentManager(), DATEPICKER);
                break;
            }
            case R.id.multi_date_button:
                MultiDatePickerDialog mdpd = MultiDatePickerDialog.newInstance(actMain.this, null);
                KurdishCalendar[] pc = new KurdishCalendar[30];
                for (int i = 0; i < pc.length; i++) {
                    pc[i] = new KurdishCalendar(System.currentTimeMillis());
                    pc[i].add(Calendar.DAY_OF_YEAR, i);
                }
                mdpd.setMinDate(pc[0]);
                mdpd.setMaxDate(pc[29]);
                //mdpd.setSelectableDays(pc);
                mdpd.setThemeDark(modeDarkDate.isChecked());
                mdpd.show(getFragmentManager(), MULTIDATEPICKER);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = "You picked the following time: " + hourString + ":" + minuteString;
        timeTextView.setText(time);
    }

    @Override
    public void onDateSet(MultiDatePickerDialog view, ArrayList<KurdishCalendar> selectedDays) {
        String date = "You picked the following dates:\n\t";
        for (KurdishCalendar calendar : selectedDays) {
            date += calendar.getKurdishDay() + "/" + (calendar.getKurdishMonth() + 1)
                    + "/" + calendar.getKurdishYear(true) + "\n\t";
        }
        multiDateTextView.setText(date);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dateTextView.setText(date);
    }
}

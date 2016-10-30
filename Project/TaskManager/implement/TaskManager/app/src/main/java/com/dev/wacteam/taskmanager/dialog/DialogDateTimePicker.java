package com.dev.wacteam.taskmanager.dialog;

import android.app.Activity;

import com.dev.wacteam.taskmanager.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class DialogDateTimePicker {
    public static void showDatePicker(int startYear, int endYear, Activity activity, OnGetDateTimeListener listener) {
        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        listener.onChange(view, year, monthOfYear, dayOfMonth);
                    }
                },
                now.get(Calendar.DAY_OF_MONTH),
                now.get(Calendar.MONTH),
                now.get(Calendar.YEAR)
        );
        dpd.setYearRange(startYear, endYear);
        dpd.show(activity.getFragmentManager(), activity.getString(R.string.date_picker_dialog));
    }

    public static void showTimePicker(Activity activity, OnGetTimeListener listener) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        listener.onChange(view, hourOfDay, minute, second);
                    }
                },
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND), false);

        tpd.show(activity.getFragmentManager(), "Timepickerdialog");
    }

    //    public void OpenTimePicker() {
//        Calendar now = Calendar.getInstance();
//        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
//
//                    }
//                }, now.get(Calendar.HOUR),
//                now.get(Calendar.MINUTE),
//                now.get(Calendar.SECOND),false);
//        timePickerDialog.show(getFragmentManager(),"Timepickerdialog");
//    }
    public interface OnGetDateTimeListener {
        public void onChange(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
    }

    public interface OnGetTimeListener {
        public void onChange(RadialPickerLayout view, int hourOfDay, int minute, int second);
    }


}

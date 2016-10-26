package com.dev.wacteam.taskmanager.dialog;

import android.app.Activity;

import com.dev.wacteam.taskmanager.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by giuse96suoire on 10/23/2016.
 */

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

    public static void showTimePicker(int startYear, int endYear, Activity activity, OnGetDateTimeListener listener) {
        Calendar now = Calendar.getInstance();

    }

    public interface OnGetDateTimeListener {
        public void onChange(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
    }

}
